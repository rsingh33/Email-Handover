package com.citi.isg.omc.issueDatabase;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;




@SpringComponent
@UIScope
@Route("issue-editor")
public class IssueEditor extends VerticalLayout implements KeyNotifier {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH.mm");
    private final IssueRepository repository;
    /* Fields to edit properties in Handover entity */
    TextField issueDescription = new TextField("Issue");
    TextField solution = new TextField("Solution");
    TextField workaround = new TextField("Workaround");
    TextField jira = new TextField("Jira");
    TextField attendee = new TextField("Attended By");


    /**
     * +
     * Action buttons for saving deleting and undoling handover actions
     */
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button undo = new Button("undo",VaadinIcon.BACKWARDS.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, undo, delete);
    Binder<Issue> binder = new Binder<>(Issue.class);
    /**
     * The currently edited handover row
     */
    private Issue issue;
    private ChangeHandler changeHandler;

    @Autowired
    public IssueEditor(IssueRepository repository) {
        this.repository = repository;

        add(issueDescription, solution, workaround, jira, attendee, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        undo.addClickListener(e -> updateIssue(issue));

        save.addClickListener( e-> {
            save.getUI().ifPresent(ui -> ui.navigate("issues"));
        });
        delete.addClickListener( e-> {
            delete.getUI().ifPresent(ui -> ui.navigate("issues"));
        });
        undo.addClickListener( e-> {
            undo.getUI().ifPresent(ui -> ui.navigate("issues"));
        });

        setVisible(false);
    }

    void delete() {
        repository.delete(this.issue);
        changeHandler.onChange();
    }

    void save() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.issue.setLastMod(sdf.format(timestamp));

        repository.save(this.issue);
        changeHandler.onChange();
    }



    /**
     * +
     * Updates handover row
     *
     * @param issue
     */
    public final void updateIssue(Issue issue) {
        if (issue == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = issue.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            this.issue = repository.findById(issue.getId()).get();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            this.issue.setLastMod(sdf.format(timestamp));
        } else {
            this.issue = issue;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            this.issue.setLastMod(sdf.format(timestamp));
        }
        undo.setVisible(persisted);

        // Bind handover properties to similarly named fields
        // moving values from fields to entities before saving
        binder.setBean(this.issue);

        setVisible(true);

        // Focus first name initially

    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public interface ChangeHandler {
        void onChange();
    }

}