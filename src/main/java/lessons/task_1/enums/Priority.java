package lessons.task_1.enums;

public enum Priority {

    P1(1),
    P2(2),
    P3(3),
    P4(4),
    P5(5),
    P6(6),
    P7(7),
    P8(8),
    P9(9),
    P10(10);

    private final int value;

    Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
