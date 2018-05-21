package demo;

import java.util.ArrayList;
import java.util.List;

public class Wrapper {

    private long processingTime;
    private List<Quota> quotas = new ArrayList<>();

    public Wrapper() {
    }

    public Wrapper(long processingTime, List<Quota> quotas) {
        this.processingTime = processingTime;
        this.quotas = quotas;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }

    public List<Quota> getQuotas() {
        return quotas;
    }

    public void setQuotas(List<Quota> quotas) {
        this.quotas = quotas;
    }

    public Wrapper updateQuotas(List<Quota> quotas) {
        this.quotas = quotas;
        return this;
    }
}