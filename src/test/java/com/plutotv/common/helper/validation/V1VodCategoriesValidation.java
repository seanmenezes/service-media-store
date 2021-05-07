package com.plutotv.common.helper.validation;

import com.plutotv.common.model.bussines.v1.vod.category.VodCategory;
import io.qameta.allure.Step;

import java.util.List;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class V1VodCategoriesValidation {
    @Step
    public void assertThatVodCategoriesAttributesNotNullEmptyAndHaveExpectedFormat(VodCategory vodCategory) {
        assertSoftly(softly -> {
            softly.assertThat(vodCategory.getId())
                    .as("id attr of vodCategory should be present, not empty and match pattern, but it is %s",
                            vodCategory.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(vodCategory.getName())
                    .as("name attr should be present, not empty, but it is %s for vodCategory %s",
                            vodCategory.getName(), vodCategory.getId())
                    .isNotEmpty();
            //description can be empty
            softly.assertThat(vodCategory.getDescription())
                    .as("description attr should be present, but it is %s for vodCategory %s",
                            vodCategory.getDescription(), vodCategory.getId())
                    .isNotNull();
            softly.assertThat(vodCategory.getPlutoOfficeOnly())
                    .as("plutoOfficeOnly attr should be present, not empty, but it is %s for vodCategory %s",
                            vodCategory.getPlutoOfficeOnly(), vodCategory.getId())
                    .isNotNull()
                    .isIn(true, false);
            softly.assertThat(vodCategory.getOrder())
                    .as("order attr should be present and not zero, but it is %s for vodCategory %s",
                            vodCategory.getOrder(), vodCategory.getId())
                    .isNotNull()
                    .isGreaterThan(0);

            softly.assertThat(vodCategory.getRegionFilter())
                    .as("regionFilter attr should be present, but it is %s for vodCategory %s",
                            vodCategory.getRegionFilter(), vodCategory.getId())
                    .isNotNull();
            if (nonNull(vodCategory.getRegionFilter())) {
                softly.assertThat(vodCategory.getRegionFilter().getInclude())
                        .as("regionFilter.include attr should be present and not empty, but it is %s for vodCategory %s",
                                vodCategory.getRegionFilter().getInclude(), vodCategory.getId())
                        .isNotEmpty();
            }

            softly.assertThat(vodCategory.getDistribution())
                    .as("distribution attr should be present, but it is %s for vodCategory %s",
                            vodCategory.getDistribution(), vodCategory.getId())
                    .isNotNull();
            if (nonNull(vodCategory.getDistribution())) {
                if (nonNull(vodCategory.getDistribution().getInclude())) {
                    //distribution.include optional
                    softly.assertThat(vodCategory.getDistribution().getInclude())
                            .as("distribution.include attr might contain 0 or more items, but it is %s for vodCategory %s",
                                    vodCategory.getDistribution().getInclude(), vodCategory.getId())
                            .hasSizeGreaterThanOrEqualTo(0);
                }
                if (nonNull(vodCategory.getDistribution().getExclude())) {
                    //distribution.exclude optional
                    softly.assertThat(vodCategory.getDistribution().getExclude())
                            .as("distribution.exclude attr might contain 0 or more items, but it is %s for vodCategory %s",
                                    vodCategory.getDistribution().getExclude(), vodCategory.getId())
                            .isNotEmpty();
                }
            }

            softly.assertThat(vodCategory.getIconPng())
                    .as("iconPng attr should be present, but it is %s for vodCategory %s",
                            vodCategory.getIconPng(), vodCategory.getId())
                    .isNotNull();
            if (nonNull(vodCategory.getIconPng())) {
                softly.assertThat(vodCategory.getIconPng().getPath())
                        .as("iconPng.path attr should be present and not empty, but it is %s for vodCategory %s",
                                vodCategory.getIconPng().getPath(), vodCategory.getId())
                        .isNotEmpty()
                        .startsWith("https://");
            }

            softly.assertThat(vodCategory.getIconSvg())
                    .as("iconSvg attr should be present, but it is %s for vodCategory %s",
                            vodCategory.getIconSvg(), vodCategory.getId())
                    .isNotNull();
            if (nonNull(vodCategory.getIconSvg())) {
                softly.assertThat(vodCategory.getIconSvg().getPath())
                        .as("iconSvg.path attr should be present and not empty, but it is %s for vodCategory %s",
                                vodCategory.getIconSvg().getPath(), vodCategory.getId())
                        .isNotEmpty()
                        .startsWith("https://");
            }

            softly.assertThat(vodCategory.getImageFeatured())
                    .as("imageFeatured attr should be present, but it is %s for vodCategory %s",
                            vodCategory.getImageFeatured(), vodCategory.getId())
                    .isNotNull();
            if (nonNull(vodCategory.getImageFeatured())) {
                softly.assertThat(vodCategory.getImageFeatured().getPath())
                        .as("imageFeatured.path attr should be present and not empty, but it is %s for vodCategory %s",
                                vodCategory.getImageFeatured().getPath(), vodCategory.getId())
                        .isNotEmpty()
                        .startsWith("https://");
            }

            softly.assertThat(vodCategory.getUpdatedAt())
                    .as("updatedAt attr should be present, not empty and match pattern, but it is %s for vodCategory %s",
                            vodCategory.getUpdatedAt(), vodCategory.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
        });
    }

    @Step
    public void assertThatThereAreNoVodCategoriesThatNotFulfillWithSyncFilter
            (List<VodCategory> vodCategories) {
        assertThat(vodCategories)
                .as("vodCategoriesThatShouldNotBeReturned list should be empty, but it has vodCategories %s",
                        vodCategories)
                .isEmpty();
    }

    @Step
    public void assertThatVodCategoryOffsetAsExpected(VodCategory firstVodCategoryAfterOffsetExpected,
                                                      VodCategory firstVodCategoryAfterOffsetActual) {
        assertThat(firstVodCategoryAfterOffsetExpected.getId())
                .as("First expected vod category after offset is %s but actual is %s",
                        firstVodCategoryAfterOffsetExpected, firstVodCategoryAfterOffsetActual)
                .isEqualTo(firstVodCategoryAfterOffsetActual.getId());
    }

    @Step
    public void assertThatVodCategoriesLimitAsExpected(String vodCategoriesCountExpected,
                                                       long vodCategoriesCountActual) {
        assertThat(vodCategoriesCountActual)
                .as("Only %s vod categories expected, but actually %s in response",
                        vodCategoriesCountExpected, vodCategoriesCountActual)
                .isEqualTo(parseLong(vodCategoriesCountExpected));
    }
}
