package ui;

import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import data.Article;
import data.User;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextArea;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;
import services.UserDAO;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@CDIView
public class SignUpView extends AbstractForm<User> implements View {

    @Inject
    private UserDAO userDAO;

    @PropertyId("username")
    private MTextField username = new MTextField("enter username");

    @PropertyId("password")
    private MPasswordField password = new MPasswordField("enetr password");


    @PostConstruct
    private void onPostConstruct() {
        setEntity(new User());
        setSavedHandler(e -> {
            userDAO.persist(getEntity());
            UI.getCurrent().getNavigator().navigateTo("start");
        });
    }


    @Override
    protected Component createContent() {
        return new MVerticalLayout( username, password, getToolbar());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
