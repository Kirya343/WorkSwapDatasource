package org.workswap.datasource.central.repository.listing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.workswap.datasource.central.model.Listing;
import org.workswap.datasource.central.model.listingModels.Category;
import org.workswap.datasource.central.model.listingModels.Location;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class ListingRepositoryCustomImpl implements ListingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Listing> findListings(List<Category> categories,
                                    String locationName,
                                    String search,
                                    Boolean requireReviews,
                                    List<String> languages,
                                    int page,
                                    int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Listing> query = cb.createQuery(Listing.class);
        Root<Listing> root = query.from(Listing.class);

        List<Predicate> predicates = new ArrayList<>();

        // 🔹 Категория по всем дочерним категориям
        if (categories != null && !categories.isEmpty()) {
            Join<Listing, Category> categoryJoin = root.join("category", JoinType.INNER);
            predicates.add(categoryJoin.in(categories));
        }

        // 🔹 Локация по имени
        if (locationName != null && !locationName.isBlank()) {
            Join<Listing, Location> locationJoin = root.join("location", JoinType.INNER);
            Join<Location, Location> countryJoin = locationJoin.join("country", JoinType.LEFT);

            Predicate cityPredicate = cb.equal(cb.lower(locationJoin.get("name")), locationName.toLowerCase());
            Predicate countryPredicate = cb.equal(cb.lower(countryJoin.get("name")), locationName.toLowerCase());

            predicates.add(cb.or(cityPredicate, countryPredicate));
        }

        // 🔹 Поиск через translations
        if (search != null && !search.isBlank()) {
            Join<Object, Object> t = root.join("translations", JoinType.INNER);
            String pattern = "%" + search.toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(t.get("title")), pattern),
                cb.like(cb.lower(t.get("description")), pattern)
            ));
        }

        // 🔹 Языки / communities
        if (languages != null && !languages.isEmpty()) {
            Join<Object, Object> c = root.join("communities", JoinType.INNER);
            predicates.add(c.in(languages));
        }

        // 🔹 Наличие отзывов
        if (requireReviews != null && requireReviews) {
            predicates.add(cb.gt(root.get("rating"), 0));
        }

        // 🔹 Активные, не тестовые и не временные
        predicates.add(cb.isTrue(root.get("active")));
        predicates.add(cb.isFalse(root.get("temporary")));
        predicates.add(cb.isFalse(root.get("testMode")));

        // 🔹 Собираем запрос
        query.select(root).distinct(true)
            .where(cb.and(predicates.toArray(new Predicate[0])))
            .orderBy(cb.desc(root.get("createdAt")));

        return entityManager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }
}
