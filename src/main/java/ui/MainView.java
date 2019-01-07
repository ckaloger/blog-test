package ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import data.Article;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import services.ArticleDAO;
import services.UserDAO;
import ui.ArticleForm;

import java.util.List;

@CDIView
public class MainView extends MVerticalLayout implements View {

    @Inject
    private ArticleDAO articleDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private ArticleForm articleForm;

    private Grid grid = new Grid();
    private BeanItemContainer<Article> container = new BeanItemContainer<>(Article.class);

    private Button addArticleButton = new Button("Add new article");

    private Button logOut = new Button("log out");

    @PostConstruct
    private void onPostConstruct() {

        String currentUsername = (String)UI.getCurrent().getSession().getAttribute("currentUser");
        setSizeFull();

        grid.setSizeFull();
        grid.setColumns("title", "context", "username");
        grid.setContainerDataSource(container);
        grid.addItemClickListener(e -> {
            if (e.isDoubleClick()){
                String articleOwnerUsername = ((Article)e.getItemId()).getUsername();
                if(articleOwnerUsername.equals(currentUsername)) {
                   articleForm.setVisible(true);
                   articleForm.setEntity((Article) e.getItemId());
              }
            }
        });

        articleForm.setSizeFull();
        articleForm.getSaveButton().addClickListener(e -> updateList());
        articleForm.getDeleteButton().addClickListener(e -> updateList());

        addArticleButton.addClickListener(e -> {
            articleForm.setEntity(new Article());
            articleForm.setVisible(true);
        });

        logOut.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo("start");
        });

        MHorizontalLayout horLayout1 = new MHorizontalLayout()
                .withSize(MSize.FULL_SIZE)
                .add(addArticleButton,.9f)
                .add(logOut,.1f);


        MHorizontalLayout horLayout2 = new MHorizontalLayout()
                .withSize(MSize.FULL_SIZE)
                .add(grid, .6f)
                .add(articleForm, .4f);

        add(horLayout1,0.1f)
                .add(horLayout2,0.9f);
    }

    private void updateList() {
        container.removeAllItems();
        container.addAll(articleDAO.listAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        articleForm.setEntity(null);
        articleForm.setVisible(false);
        updateList();
    }

}
