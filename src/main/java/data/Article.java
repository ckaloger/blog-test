package data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.enterprise.context.SessionScoped;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@EqualsAndHashCode
@ToString
@SessionScoped
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer id;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String context;

    @ManyToOne
    @JoinColumn(name = "userId")
    @Getter @Setter
    private User user;

    public String getUsername(){
        return user.getUsername();
    }
}
