package by.drobmax.stopwatchtesttask.models;

/**
 * Created by Admin on 21.08.2015.
 */
public class TimeListModel {
    private long id;
    private String timeList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeListModel that = (TimeListModel) o;

        if (id != that.id) return false;
        return !(timeList != null ? !timeList.equals(that.timeList) : that.timeList != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (timeList != null ? timeList.hashCode() : 0);
        return result;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTimeList() {
        return timeList;
    }

    public void setTimeList(String timeList) {
        this.timeList = timeList;
    }

    public TimeListModel(long id, String timeList) {
        this.id = id;
        this.timeList = timeList;
    }
}
