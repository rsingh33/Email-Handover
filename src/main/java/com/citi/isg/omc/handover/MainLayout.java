package com.citi.isg.omc.handover;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;


import static com.citi.isg.omc.handover.Constants.VIEWPORT;


@Route("handover")
@HtmlImport("src/main-layout.html")
@StyleSheet("styles/shared-styles.html")
@PageTitle("Email Handover")
@Viewport(VIEWPORT)
public class MainLayout extends VerticalLayout {


    final Grid<HandoverRow> grid;
    final TextField filter;
    private final HandoverRepository repo;
    private final HandoverEditor editor;
    private final Button addNewBtn;
    private final Button sendEmail;
    private final Button export;
    private final Button issuesButton;

    public MainLayout(HandoverRepository repo, HandoverEditor editor) {

        UI.getCurrent().getPage().setTitle("OMC Email Handover");
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(HandoverRow.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Add Row", VaadinIcon.PLUS.create());
        this.sendEmail = new Button("Send EmailHandover", VaadinIcon.ENVELOPE.create());
        this.export = new Button("Export", VaadinIcon.BRIEFCASE.create());
        this.issuesButton = new Button("Issues", VaadinIcon.AIRPLANE.create());
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn, sendEmail,export, issuesButton);
        add(actions, grid, editor);

        grid.setSizeFull();
        grid.setHeightByRows(true);
        grid.setColumns("email", "tracking", "comments", "reportedBy", "lastMod", "status", "environment" , "currentlyWith");
        grid.getColumnByKey("lastMod").setSortable(true).setResizable(true);
        grid.getColumnByKey("reportedBy").setSortable(true).setResizable(true);
        grid.getColumnByKey("status").setResizable(true);
        grid.getColumnByKey("email").setResizable(true);
        grid.getColumnByKey("comments").setResizable(true);
        grid.getColumnByKey("tracking").setResizable(true);
        grid.getColumnByKey("environment").setResizable(true);
        grid.setDetailsVisibleOnClick(true);
        grid.setColumnReorderingAllowed(true);

        addNewBtn.getElement().setAttribute("theme", "contrast primary");
        issuesButton.getElement().setAttribute("theme", "contrast primary");
        sendEmail.getElement().setAttribute("theme", "contrast primary");
        export.getElement().setAttribute("theme", "contrast primary");
        filter.setPlaceholder("Filter by reporter");


        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> getHandover(e.getValue()));



        // Connect selected row to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {

            editor.updateHandover(e.getValue());

        });


       grid.asSingleSelect().addValueChangeListener(e -> {
            grid.getUI().ifPresent(ui -> ui.navigate("editor"));
        });


        addNewBtn.addClickListener(e ->{

                editor.updateHandover(new HandoverRow());

        });
        issuesButton.addClickListener(e -> {
            issuesButton.getUI().ifPresent(ui -> ui.navigate("issues"));
        });
       addNewBtn.addClickListener(e -> {
            addNewBtn.getUI().ifPresent(ui -> ui.navigate("editor"));
        });

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
