package com.example.crmplus.order.repository;

import com.example.crmplus.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository extends JpaRepository<Order, Long> {
    /*@Query("""
  select distinct o from Order o
  left join fetch o.items i
  left join fetch o.client c
""")
    List<Order> findAllWithItemsAndClient();

    @Query("""
  select distinct o from Order o
  left join fetch o.items i
  left join fetch o.client c
  where o.client.id = :clientId
""")
    List<Order> findByClientIdWithItemsAndClient(Long clientId);

    @Query("""
  select distinct o from Order o
  left join fetch o.items i
  left join fetch o.client c
  where o.status = :status
""")
    List<Order> findByStatusWithItemsAndClient(Order.Status status);*/

    /*@EntityGraph(attributePaths = {"items", "client"})
    Page<Order> findAll(Pageable pageable);*/

    @EntityGraph(attributePaths = {"items", "client"})
    Page<Order> findByClientId(Long clientId, Pageable pageable);

    @EntityGraph(attributePaths = {"items", "client"})
    Page<Order> findByStatus(Order.Status status, Pageable pageable);
}