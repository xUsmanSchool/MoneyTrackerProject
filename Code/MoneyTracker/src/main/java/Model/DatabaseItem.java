package Model;

import HelperClass.Date;
import HelperClass.HashMep;
import java.time.LocalDate;

public abstract class DatabaseItem {
    protected final HashMep<String, LocalDate> creationDate;
    protected final HashMep<String, LocalDate> editDate;
    protected final HashMep<String, String> icon;

    protected DatabaseItem() {
        creationDate = new HashMep<>();
        editDate = new HashMep<>();
        icon = new HashMep<>();
    }

    public String getCreationDateKey() {
        return this.creationDate.getKey();
    }
    public LocalDate getCreationDateValue() {
        return this.creationDate.getValue();
    }
    public void setCreationDateValue(LocalDate date){
        this.creationDate.put(getCreationDateKey(), date);
    }

    public String getEditDateKey() {
        return this.editDate.getKey();
    }
    public LocalDate getEditDateValue() {
        return this.editDate.getValue();
    }

    public String getIconKey() {
        return this.icon.getKey();
    }
    public String getIconValue() {
        return this.icon.getValue();
    }
    public abstract void setIcon(String iconUrl);
    protected abstract void updateAccountEditDate();
}
