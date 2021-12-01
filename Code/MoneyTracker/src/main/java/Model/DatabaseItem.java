package Model;

import HelperClass.Date;
import HelperClass.HashMep;

public abstract class DatabaseItem {
    protected final HashMep<String, Date> creationDate;
    protected final HashMep<String, Date> editDate;
    protected final HashMep<String, String> icon;

    protected DatabaseItem() {
        creationDate = new HashMep<>();
        editDate = new HashMep<>();
        icon = new HashMep<>();
    }

    public String getCreationDateKey() {
        return this.creationDate.getKey();
    }
    public Date getCreationDateValue() {
        return this.creationDate.getValue();
    }

    public String getEditDateKey() {
        return this.editDate.getKey();
    }
    public Date getEditDateValue() {
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
