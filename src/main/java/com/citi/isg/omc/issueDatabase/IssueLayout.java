package com.citi.isg.omc.issueDatabase;


import com.citi.isg.omc.issueDatabase.Issue;
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
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.StringUtils;


@Route("issues")
@PageTitle("OMC issues")
public class IssueLayout extends VerticalLayout {


    final Grid<Issue> grid;
    final TextField filter;
    private final IssueRepository repo;
    private final IssueEditor editor;

    private final Button addNewBtn;
    private final Button handoverButton;

    public IssueLayout(IssueRepository repo, IssueEditor editor) {

        UI.getCurrent().getPage().setTitle("OMC Issues");
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Issue.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Add Issue", VaadinIcon.PLUS.create());
        this.handoverButton = new Button("Handover", VaadinIcon.AIRPLANE.create());
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn, handoverButton);
        add(actions, grid, editor);

        grid.setSizeFull();
        grid.setHeightByRows(true);
        grid.setColumns( "issueDescription", "solution", "workaround", "jira", "attendee", "lastMod");

        grid.getColumnByKey("lastMod").setSortable(true).setResizable(true);
        grid.getColumnByKey("jira").setSortable(true).setResizable(true);
        grid.getColumnByKey("issueDescription").setResizable(true);
        grid.getColumnByKey("solution").setResizable(true);
        grid.getColumnByKey("workaround").setResizable(true);

        grid.setDetailsVisibleOnClick(true);
        grid.setColumnReorderingAllowed(true);

        addNewBtn.getElement().setAttribute("theme", "contrast primary");
        handoverButton.getElement().setAttribute("theme", "contrast primary");
        filter.setPlaceholder("Filter by issueDescription");


        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> getIssues(e.getValue()));


        // Connect selected row to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {

            editor.updateIssue(e.getValue());

        });


        grid.asSingleSelect().addValueChangeListener(e -> {
            grid.getUI().ifPresent(ui -> ui.navigate("issue-editor"));
        });


        addNewBtn.addClickListener(e -> {

            editor.updateIssue(new Issue());

        });

        addNewBtn.addClickListener(e -> {
            addNewBtn.getUI().ifPresent(ui -> ui.navigate("issue-editor"));
        });

        handoverButton.addClickListener(e -> {
            handoverButton.getUI().ifPresent(ui -> ui.navigate("handover"));
        });

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            getIssues(filter.getValue());
        });

        // Initialize listing
        getIssues(null);


    }


    private void getIssues(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.findByIssueDescriptionStartsWithIgnoreCase(filterText));
        }
    }

}
