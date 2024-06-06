// 22012104 윤선중
import java.util.*;

class Date {
    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getDate() {
        return year + "-" + month + "-" + day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return year == date.year && month == date.month && day == date.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }
}

class User {
    private String name;
    private int bodyWeight;

    public User(String name, int bodyWeight) {
        this.name = name;
        this.bodyWeight = bodyWeight;
    }

    public int getBodyWeight() {
        return bodyWeight;
    }

    public String getName() {
        return name;
    }
}

class Record {
    private int weightKg;
    private int reps;
    private int setCount;

    public Record(int weightKg, int reps, int setCount) {
        this.weightKg = weightKg;
        this.reps = reps;
        this.setCount = setCount;
    }

    public int getPower() {
        return weightKg * reps * setCount;
    }

    public int getWeightKg() {
        return weightKg;
    }

    public int getReps() {
        return reps;
    }

    public int getSetCount() {
        return setCount;
    }
}

class History {
    public Date date;
    public Record record;
    public ExTypes exTypes;

    public History(Date date, Record record, ExTypes exTypes) {
        this.date = date;
        this.record = record;
        this.exTypes = exTypes;
    }

    public void showHistory() {
        System.out.println("날짜: " + date.getDate());
        System.out.println("운동량: " + record.getPower());
        System.out.println("운동종목: " + exTypes.getTypes());
    }

    public void showSimpleHistory() {
        System.out.println("날짜: " + date.getDate());
        System.out.println("운동종목: " + exTypes.getTypes());
        System.out.println("무게: " + record.getWeightKg() + "kg");
        System.out.println("반복수: " + record.getReps());
        System.out.println("세트수: " + record.getSetCount());
        System.out.println();
    }
}

class ChangeView {
    private int amount;
    private Date date;

    public ChangeView(int amount, Date date) {
        this.amount = amount;
        this.date = date;
    }

    public void showChange() {
        System.out.println("날짜: " + date.getDate());
        System.out.println("총운동량: " + amount);
        System.out.println();
    }
}

class Amount {
    private int power;
    private int weight;
    private ExTypes exTypes;

    public Amount(int power, int weight, ExTypes exTypes) {
        this.power = power;
        this.weight = weight;
        this.exTypes = exTypes;
    }

    public int getAmount() {
        int multiplier = 1;
        if (exTypes.isBenchPress()) {
            multiplier = 3;
        } else if (exTypes.isDeadLift()) {
            multiplier = 4;
        } else if (exTypes.isSquat()) {
            multiplier = 5;
        }
        return power * weight * multiplier;
    }
}

class ExTypes {
    private boolean benchPress;
    private boolean deadLift;
    private boolean squat;

    public ExTypes(boolean benchPress, boolean deadLift, boolean squat) {
        if ((benchPress && deadLift) || (benchPress && squat) || (deadLift && squat)) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        this.benchPress = benchPress;
        this.deadLift = deadLift;
        this.squat = squat;
    }

    public boolean isBenchPress() {
        return benchPress;
    }

    public boolean isDeadLift() {
        return deadLift;
    }

    public boolean isSquat() {
        return squat;
    }

    public String getTypes() {
        if (benchPress) return "Bench Press";
        if (deadLift) return "Dead Lift";
        if (squat) return "Squat";
        return "None";
    }
}

public class Control {
    private static List<History> histories = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("이름을 입력해주세요: ");
        String name = scanner.nextLine();

        System.out.print("몸무게를 입력해주세요: ");
        int bodyWeight = scanner.nextInt();

        User user = new User(name, bodyWeight);

        while (true) {
            System.out.print("현재 year을 입력해주세요: ");
            int year = scanner.nextInt();

            System.out.print("현재 month를 입력해주세요: ");
            int month = scanner.nextInt();

            System.out.print("현재 day를 입력해주세요: ");
            int day = scanner.nextInt();

            Date date = new Date(year, month, day);

            while (true) {
                System.out.println("1. 운동기록 Write");
                System.out.println("2. 운동기록 Read");
                System.out.println("3. 날짜 변경하기");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    recordWorkout(scanner, date);
                } else if (choice == 2) {
                    viewWorkout(scanner, date);
                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println("잘못된 입력입니다.");
                }
            }
        }
    }

    private static void recordWorkout(Scanner scanner, Date date) {
        System.out.println("오늘은 어떤 운동을 하셨나요?:");
        System.out.println("1. Bench Press");
        System.out.println("2. Dead Lift");
        System.out.println("3. Squat");
        int exTypeChoice = scanner.nextInt();

        ExTypes exTypes = null;
        switch (exTypeChoice) {
            case 1:
                exTypes = new ExTypes(true, false, false);
                break;
            case 2:
                exTypes = new ExTypes(false, true, false);
                break;
            case 3:
                exTypes = new ExTypes(false, false, true);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }

        System.out.print("운동했던 무게(kg): ");
        int weightKg = scanner.nextInt();

        System.out.print("운동했던 갯수: ");
        int reps = scanner.nextInt();

        System.out.print("운동했던 세트수: ");
        int setCount = scanner.nextInt();

        Record record = new Record(weightKg, reps, setCount);
        History history = new History(date, record, exTypes);
        histories.add(history);

        System.out.println("Workout recorded.");
        System.out.println();
    }

    private static void viewWorkout(Scanner scanner, Date date) {
        System.out.println("1. 단순 운동기록 보기");
        System.out.println("2. 수치화된 운동량 보기");
        int choice = scanner.nextInt();

        if (choice == 1) {
            for (History history : histories) {
                history.showSimpleHistory();
            }
        } else if (choice == 2) {
            Map<String, Integer> dateAmountMap = new HashMap<>();
            for (History history : histories) {
                String historyDate = history.date.getDate();
                Record record = history.record;
                Amount amount = new Amount(record.getPower(), 2, history.exTypes);
                int totalAmount = amount.getAmount();
                dateAmountMap.put(historyDate, dateAmountMap.getOrDefault(historyDate, 0) + totalAmount);
            }

            for (Map.Entry<String, Integer> entry : dateAmountMap.entrySet()) {
                ChangeView changeView = new ChangeView(entry.getValue(), new Date(Integer.parseInt(entry.getKey().split("-")[0]), Integer.parseInt(entry.getKey().split("-")[1]), Integer.parseInt(entry.getKey().split("-")[2])));
                changeView.showChange();
            }
        } else {
            System.out.println("잘못된 입력입니다.");
        }
    }
}
