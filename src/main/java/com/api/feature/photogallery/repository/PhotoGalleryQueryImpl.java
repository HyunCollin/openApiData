package com.api.feature.photogallery.repository;

import com.api.feature.photogallery.entity.PhotoGallery;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.api.feature.photogallery.entity.QPhotoGallery.photoGallery;

@RequiredArgsConstructor
public class PhotoGalleryQueryImpl implements PhotoGalleryQuery {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<PhotoGallery> findBySearchKeyWordLike(String searchKeyword) {

        return jpaQueryFactory.select(photoGallery)
                .from(photoGallery)
                .where(photoGallery.galSearchKeyword.like("%"+searchKeyword+"%"))
                .fetch();
    }

    public BooleanExpression getSearchExpression(String galTitle) {
        if (StringUtils.hasText(galTitle)) {
            return photoGallery.galTitle.like(galTitle);
        }
        return null;
    }
}
