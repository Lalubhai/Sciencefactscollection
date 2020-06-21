package patel.krupesh.sciencefactscollection;

public class Message {
    private int msmId;
    private String smsBody;
    private int smsFavorite;
    private int type;

    public int getMsmId() {
        return this.msmId;
    }

    public void setMsmId(int msmId) {
        this.msmId = msmId;
    }

    public String getSmsBody() {
        return this.smsBody;
    }

    public void setSmsBody(String smsBody) {
        this.smsBody = smsBody;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSmsFavorite() {
        return this.smsFavorite;
    }

    public void setSmsFavorite(int smsFavorite) {
        this.smsFavorite = smsFavorite;
    }

    public Message(int msmId, String smsBody, int type, int smsFavorite) {
        this.msmId = msmId;
        this.smsBody = smsBody;
        this.type = type;
        this.smsFavorite = smsFavorite;
    }





}
