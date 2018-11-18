package com.popov.Repository;

import com.popov.domain.Shop;
import com.popov.domain.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuppliersRepository extends JpaRepository<Suppliers, Long> {
}
