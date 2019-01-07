package services;

import data.Article;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class ArticleDAO {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public void save(Article article) {
        if (article.getId() == null) {
            manager.persist(article);

        } else {
            manager.merge(article);
        }
    }

    @Transactional
    public void remove(Article article){
        System.out.println("articledao remove article id = " + article.getId());
        System.out.println("article title = " + article.getTitle());
        manager.remove(manager.contains(article) ? article : manager.merge(article));
        //manager.remove(article);
        System.out.println("meta");
    }

    @Transactional
    public void remove(int id){
//        System.out.println("userdao remove article id = " + article.getId());
//        System.out.println("article title = " + article.getTitle());
        manager.remove(find(id));
    }


    @Transactional
    public List<Article> listAll() {
        return manager.createQuery("SELECT a FROM Article a", Article.class).getResultList();
    }




    public int find(int userId,int id){
        try {

            if  (manager
                    .createQuery("select a from Article a where a.id = :id ", Article.class)
                    .setParameter("id", id)
                    // .setParameter("userId", userId)
                    .getSingleResult() != null)
                return 1;
        }
        catch (NoResultException e) {
            return -1;
        }
        return -1;
    }


    public Article find(int id){
        try {

            return  manager
                    .createQuery("select a from Article a where a.id = :id", Article.class)
                    .setParameter("id", id)
                    .getSingleResult();

        }
        catch (NoResultException e) {
            return null;
        }
    }
}
