package org.mandulis.mts.data.repository;

import org.mandulis.mts.data.entity.Category;
import org.mandulis.mts.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}
