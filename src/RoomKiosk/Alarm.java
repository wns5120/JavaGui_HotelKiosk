package RoomKiosk;

public class Alarm {
    private int id;
    private String ampm;
    private int hour;
    private int minute;
    private boolean everyday;
    private String selectedDays;
    private boolean isOn;

    public Alarm(int id, String ampm, int hour, int minute, boolean everyday, String selectedDays) {
        this.id = id;
        this.ampm = ampm;
        this.hour = hour;
        this.minute = minute;
        this.everyday = everyday;
        this.selectedDays = selectedDays;
        this.isOn = isOn;
    }

    public int getId() {
        return id;
    }

    public String get_ampm() {
        return ampm;
    }

    public int get_hour() {
        return hour;
    }

    public int get_minute() {
        return minute;
    }

    public boolean isEveryday() {
        return everyday;
    }

    public String get_SelectedDays() {
        return selectedDays;
    }

    public boolean isOn() {
        return isOn;
    }
}