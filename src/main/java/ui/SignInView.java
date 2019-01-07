package ui;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import data.User;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;
import services.UserDAO;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView
public class SignInView extends AbstractForm<User> implements View {

    @Inject
    UserDAO userDAO;
    @PropertyId("username")
    private MTextField username = new MTextField("enter username");

    @PropertyId("password")
    private MPasswordField password = new MPasswordField("enetr password");
    Button b =new Button("log in");

    @PostConstruct
    private void OnPostConstruct(){

        setEntity(new User());
        b.addClickListener(e -> {
            if ( userDAO.findWithUsernameAndPassword(getEntity().getUsername(),getEntity().getPassword()) != null) {
                UI.getCurrent().getSession().setAttribute("currentUser",username.getValue());
                UI.getCurrent().getNavigator().navigateTo("main");
            }
            else {
                setEntity(new User());
            }

        });
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout( username, password,b, getSaveButton(), getToolbar());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        }
}
