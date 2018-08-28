package emailHandover;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.util.StringUtils;


@Route("")
@HtmlImport("frontend://styles/shared-styles.html")
@Theme(value = Lumo.class)
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainLayout extends VerticalLayout {


    private final HandoverRepository repo;
    final Grid<HandoverRow> grid;
    private final HandoverEditor editor;
    final TextField filter;
    private final Button addNewBtn;
    private final Button sendEmail;



    public MainLayout(HandoverRepository repo, HandoverEditor editor) {

        UI.getCurrent().getPage().setTitle("OMC Email Handover");
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(HandoverRow.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Add Row", VaadinIcon.PLUS.create());
        this.sendEmail = new Button("Send EmailHandover", VaadinIcon.ENVELOPE.create());


        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn, sendEmail);
        add(actions, grid, editor);

        grid.setHeightByRows(true);
        grid.setColumns("id", "email", "tracking", "comments", "reportedBy", "lastMod");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.getColumnByKey("lastMod").setSortable(true);
        grid.getColumnByKey("reportedBy").setSortable(true);
        grid.getColumnByKey("id").setSortable(true);
        grid.setColumnReorderingAllowed(true);
        addNewBtn.getElement().setAttribute("theme", "contrast primary");
        filter.setPlaceholder("Filter by reporter");


        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> getHandover(e.getValue()));

        //  emailField.setValueChangeMode(ValueChangeMode.EAGER);


        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.updateHandover(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.updateHandover(new HandoverRow()));

        sendEmail.addClickListener(e -> editor.sendEmail());

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            getHandover(filter.getValue());
        });

        // Initialize listing
        getHandover(null);

        schedule(repo);


    }

    private void schedule(HandoverRepository repo) {
        EmailScheduler emailScheduler = new EmailScheduler(repo);

        try {
            emailScheduler.schedule();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getHandover(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.findByReportedByStartsWithIgnoreCase(filterText));
        }
    }


}
