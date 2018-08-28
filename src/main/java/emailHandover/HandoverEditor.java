package emailHandover;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


/**
 * +
 */


@SpringComponent
@UIScope
public class HandoverEditor extends VerticalLayout implements KeyNotifier {

    private final HandoverRepository repository;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH.mm");


    /**
     * The currently edited handover row
     */
    private HandoverRow handoverRow;

    /* Fields to edit properties in Handover entity */
    TextField reportedBy = new TextField("Reporter");
    TextField email = new TextField("Email Subject");
    TextField tracking = new TextField("Tracking");
    TextField comments = new TextField("Comments");
    TextField lastMod = new TextField("Last Modified");


    /**
     * +
     * Action buttons for saving deleting and cancelling handover actions
     */
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<HandoverRow> binder = new Binder<>(HandoverRow.class);
    private ChangeHandler changeHandler;

    @Autowired
    public HandoverEditor(HandoverRepository repository) {
        this.repository = repository;

        add(reportedBy, email, tracking, comments, lastMod, actions);

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
        cancel.addClickListener(e -> updateHandover(handoverRow));
        setVisible(false);
    }

    void delete() {
        repository.delete(this.handoverRow);
        changeHandler.onChange();
    }

    void save() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.handoverRow.setLastMod(sdf.format(timestamp));

        repository.save(this.handoverRow);
        changeHandler.onChange();
    }


    public void sendEmail() {
        ListToHtmlTransformer converter = new ListToHtmlTransformer();
        SendEmail email = new SendEmail();
        String content = converter.compose(repository.findAll());
        email.emailSend(content);
    }

    public interface ChangeHandler {
        void onChange();
    }

    /**+
     * Updates handover row
     *
     * @param row
     */
    public final void updateHandover(HandoverRow row) {
        if (row == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = row.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            handoverRow = repository.findById(row.getId()).get();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            this.handoverRow.setLastMod(sdf.format(timestamp));
        } else {
            this.handoverRow = row;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            this.handoverRow.setLastMod(sdf.format(timestamp));
        }
        cancel.setVisible(persisted);

        // Bind handover properties to similarly named fields
        // moving values from fields to entities before saving
        binder.setBean(this.handoverRow);

        setVisible(true);

        // Focus first name initially
        reportedBy.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}