import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        // Wyświetl procent produktów, które dostarczają więcej niż 50% sumy żelaza i wapnia. Zwróć wynik do dwóch miejsc po przecinku.

        var queryStringA = "SELECT ROUND (100.0 * COUNT(p) / ((SELECT COUNT (p2) FROM ProductsEntity p2)), 2) FROM ProductsEntity p WHERE p.calcium + p.iron > 50";
        Query queryA = em.createQuery(queryStringA);
        Float reasultA = (Float) queryA.getSingleResult();
        System.out.println(reasultA);

        // Wyświetl średnią wartość kaloryczną produktów z bekonem w nazwie.
        var queryStringB = "SELECT avg(calories) FROM ProductsEntity WHERE itemName like '%bacon%'";
        Query queryB = em.createQuery(queryStringB);
        Double reasultB = (Double) queryB.getSingleResult();
        System.out.println(reasultB);

        // Dla każdej z kategorii wyświetl produkt o największej wartości cholesterolu.
        var queryStringC = "SELECT p.itemName FROM ProductsEntity p " +
                "WHERE (p.category, p.cholesterole) IN " +
                "(SELECT p2.category, MAX(p2.cholesterole) FROM ProductsEntity p2 GROUP BY p2.category)";
        Query queryC = em.createQuery(queryStringC);
        List<String> reasultC = queryC.getResultList();
        System.out.println(reasultC);

        //Wyświetl liczbę kaw (Mocha lub Coffee w nazwie) niezawierających błonnika.
        var queryStringD = "SELECT count(p) FROM ProductsEntity p WHERE itemName like '%mocha%' or itemName like '%coffee%' and p.fiber = 0 ";
        Query queryD = em.createQuery(queryStringD);
        Long reasultD = (Long) queryD.getSingleResult();
        System.out.println(reasultD);

        //Wyświetl kaloryczność wszystkich McMuffinów. Wyniki wyświetl w kilodżulach (jedna kaloria to 4184 dżule) rosnąco.
        var queryStringE = "SELECT 4.184 * p.calories FROM ProductsEntity p where itemName like '%mcmuffin%'";
        Query queryE = em.createQuery(queryStringE);
        List<String> reasultE = queryE.getResultList();
        System.out.println(reasultE);

        //Wyświetl liczbę różnych wartości węglowodanów.
        var queryStringF = "SELECT count(distinct p.carbs) FROM ProductsEntity p ";
        Query queryF = em.createQuery(queryStringF);
        Long reasultF = (Long) queryF.getSingleResult();
        System.out.println(reasultF);
    }
}
