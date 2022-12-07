package neg5.domain.api.enums;

public enum PlayerYear implements StringIdentifiable {
    NA("na"),
    KINDERGARTEN("kindergarten"),
    FIRST_GRADE("first_grade"),
    SECOND_GRADE("second_grade"),
    THIRD_GRADE("third_grade"),
    FOURTH_GRADE("fourth_grade"),
    FIFTH_GRADE("fifth_grade"),
    SIXTH_GRADE("sixth_grade"),
    SEVENTH_GRADE("seventh_grade"),
    EIGHTH_GRADE("eighth_grade"),
    NINTH_GRADE("ninth_grade"),
    TENTH_GRADE("tenth_grade"),
    ELEVENTH_GRADE("eleventh_grade"),
    TWELFTH_GRADE("twelfth_grade"),
    COLLEGE_FRESHMAN("college_freshman"),
    COLLEGE_SOPHOMORE("college_sophomore"),
    COLLEGE_JUNIOR("college_junior"),
    COLLEGE_SENIOR("college_senior"),
    COLLEGE_POST_SENIOR("college_post_senior"),
    GRADUATE_STUDENT("grad_student");

    private String id;

    PlayerYear(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
