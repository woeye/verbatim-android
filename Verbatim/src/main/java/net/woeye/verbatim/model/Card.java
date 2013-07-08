package net.woeye.verbatim.model;

import java.util.Date;

public class Card {
    private long id;
    private long collectionId;
    private String front;
    private String back;
    private int level;
    private Date lastTraining;

    public Card() {
        setLevel(0);
        setCollectionId(0);
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getCollectionId() { return collectionId; }
    public void setCollectionId(long collectionId) { this.collectionId = collectionId; }

    public String getFront() { return front; }
    public void setFront(String front) { this.front = front; }

    public String getBack() { return back; }
    public void setBack(String back) { this.back = back; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public Date getLastTraining() { return lastTraining; }
    public void setLastTraining(Date lastTraining) { this.lastTraining = lastTraining; }

    @Override
    public String toString() {
        return "[Card] id:" + this.id;
    }

    @Override
    public int hashCode() {
        return (int)this.id;
    }
}
