package se.iths.service;

import se.iths.entity.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ItemService {

    @PersistenceContext
    EntityManager entityManager;

    public void createItem(Item item) {
        entityManager.persist(item);
    }

    public void updateItem(Item item) {
        entityManager.merge(item);
    }

    public Item findItemById(Long id) {
        return entityManager.find(Item.class, id);
    }

    // JPQL Dynamic Query
    public List<Item> getAllItems() {
        return entityManager.createQuery("SELECT i from Item i", Item.class).getResultList();
    }

    public void deleteItem(Long id) {
        Item foundItem = entityManager.find(Item.class, id);
        entityManager.remove(foundItem);
    }

    public Item updateName(Long id, String name) {
        Item foundItem = entityManager.find(Item.class, id);
        // Nu finns foundItem i vårat Persistence Context
        foundItem.setName(name);
        // Ändringen registreras i vårat Persistence Context
        return foundItem;
    }



    // JPQL Queries för demo

    // Dynamic Query
    public List<Item> getByNameDynamicQuery(String name) {
        String query = "SELECT i FROM Item i WHERE i.name = '" + name + "'";
        return entityManager.createQuery(query, Item.class).getResultList();
    }

    // Named Parameters
    public List<Item> getByNameNamedParameters(String name) {
        String query = "SELECT i FROM Item i WHERE i.name = :name";
        return entityManager.createQuery(query, Item.class).setParameter("name", name).getResultList();
    }

    // Positional Parameters
    public List<Item> getByNamePositionalParameters(String name) {
        String query = "SELECT i FROM Item i WHERE i.name = ?1";
        return entityManager.createQuery(query, Item.class).setParameter(1, name).getResultList();
    }

    // Where between
    public List<Item> getAllItemsBetweenPrice(double minPrice, double maxPrice) {
        String query = "SELECT i FROM Item i WHERE i.price BETWEEN :minPrice AND :maxPrice";
        return entityManager.createQuery(query, Item.class).setParameter("minPrice", minPrice)
                .setParameter("maxPrice", maxPrice).getResultList();
    }

    // Order by (alphabetically)
    public List<Item> getAllItemsSortedByCategory() {
        String query = "SELECT i FROM Item i ORDER BY i.category";
        return entityManager.createQuery(query, Item.class).getResultList();
    }

    public List<Item> getAllWithNamedQuery() {
        return entityManager.createNamedQuery("itemEntity.findAll", Item.class).getResultList();
    }

    // Select the highest value from all items
    public Double selectMaxPrice() {
        TypedQuery<Double> typedQuery = entityManager.createQuery("SELECT MAX(i.price) FROM Item i", Double.class);
        return typedQuery.getSingleResult();
    }


    // Criteria API queries

    // Get all items
    public List<Item> getAllCriteria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
        TypedQuery<Item> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    // Order by category
    public List<Item> getAllItemsSortedByCategoryCriteria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot = criteriaQuery.from(Item.class);
        criteriaQuery.orderBy(criteriaBuilder.asc(itemRoot.get("category")));
        TypedQuery<Item> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }



}
