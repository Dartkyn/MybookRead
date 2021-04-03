package com.mbkread.mybook.repo;

import com.mbkread.mybook.core.Rating;

import org.springframework.data.jpa.repository.JpaRepository;
public interface RatingRepository extends JpaRepository<Rating, Long>{

}
