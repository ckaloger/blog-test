package ui;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

//@DesignRoot
@CDIView
public class StartView extends VerticalLayout implements View {
    SignUpView signUpForm;
    public StartView(){
       // User user = new User();
        //signUpForm = new SignUpView();
        System.out.println("ui = " + UI.getCurrent().getNavigator().toString());
       // signUpForm.setEntity(user);
        Button signUpButton = new Button("Sign up");
        signUpButton.addClickListener( e -> {

            UI.getCurrent().getNavigator().navigateTo("sign-up");
        });
        addComponent(signUpButton);

        Button signInButton = new Button("Sign in");
        signInButton.addClickListener( e -> {
            UI.getCurrent().getNavigator().navigateTo("sign-in");
        });
        addComponent(signInButton);

        setMargin(true);
        setSpacing(true);
    }

    @PostConstruct
    private void onPostConstruct() {
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
