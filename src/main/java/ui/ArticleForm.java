package ui;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.*;
import com.vaadin.ui.Component;
import data.Article;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.fields.MTextArea;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import services.ArticleDAO;
import services.UserDAO;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class ArticleForm extends AbstractForm<Article> {

    @Inject
    private ArticleDAO articleDAO;

    @Inject
    private UserDAO userDAO;

    @PropertyId("title")
    private MTextField title = new MTextField("enter title").withFullWidth();

    @PropertyId("context")
    private MTextArea context = new MTextArea("enetr text").withFullWidth();

    private Button cancelButton = new Button("cancel");

    private Label label = new Label();

    @Override
    protected Component createContent() {
        setSavedHandler(e -> {
            if (getEntity() == null)
                setEntity(new Article());
            getEntity().setUser(userDAO.findWithUsername((String)UI.getCurrent().getSession().getAttribute("currentUser")));
            articleDAO.save(getEntity());
            refresh();
        });

        setDeleteHandler(e -> {
            articleDAO.remove(getEntity());
            refresh();
        });

        cancelButton.addClickListener(e -> {
            setVisible(false);
            refresh();
        });

        MHorizontalLayout buttonLayout = new MHorizontalLayout()
                .withSize(MSize.FULL_SIZE)
                .add(label,0.8f)
                .add(cancelButton,.2f);

        return new MVerticalLayout(buttonLayout,title, context, getToolbar());
    }

      private void refresh(){
          setEntity(null);
          setVisible(false);
      }

}
