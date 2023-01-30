package neg5.domain.api.enums;

public enum PlayerYear implements StringIdentifiable {
    NA("na", Integer.MIN_VALUE),
    KINDERGARTEN("kindergarten", 0),
    FIRST_GRADE("first_grade", 1),
    SECOND_GRADE("second_grade", 2),
    THIRD_GRADE("third_grade", 3),
    FOURTH_GRADE("fourth_grade", 4),
    FIFTH_GRADE("fifth_grade", 5),
    SIXTH_GRADE("sixth_grade", 6),
    SEVENTH_GRADE("seventh_grade", 7),
    EIGHTH_GRADE("eighth_grade", 8),
    NINTH_GRADE("ninth_grade", 9),
    TENTH_GRADE("tenth_grade", 10),
    ELEVENTH_GRADE("eleventh_grade", 11),
    TWELFTH_GRADE("twelfth_grade", 12),
    COLLEGE_FRESHMAN("college_freshman", 13),
    COLLEGE_SOPHOMORE("college_sophomore", 14),
    COLLEGE_JUNIOR("college_junior", 15),
    COLLEGE_SENIOR("college_senior", 16),
    COLLEGE_POST_SENIOR("college_post_senior", 17),
    GRADUATE_STUDENT("grad_student", 18);

    private String id;
    private int qbjOrdinal;

    PlayerYear(String id, int qbjOrdinal) {
        this.id = id;
        this.qbjOrdinal = qbjOrdinal;
    }

    @Override
    public String getId() {
        return id;
    }

    public int getQbjOrdinal() {
        return qbjOrdinal;
    }
}
