package com.jeft.testother;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PressureRecord implements Parcelable {
    public int id;

    //记录时间
    public long recordTime;

    //舒张压
    public int diastolic;

    //收缩压
    public int systolic;

    //脉搏
    public int pulse;

    public PressureRecord(int id,long recordTime, int diastolic, int systolic, int pulse) {
        this.id = id;
        this.recordTime = recordTime;
        this.diastolic = diastolic;
        this.systolic = systolic;
        this.pulse = pulse;
    }

    public PressureRecord() {

    }

    protected PressureRecord(Parcel in) {
        id = in.readInt();
        recordTime = in.readLong();
        diastolic = in.readInt();
        systolic = in.readInt();
        pulse = in.readInt();
    }

    public static final Creator<PressureRecord> CREATOR = new Creator<PressureRecord>() {
        @Override
        public PressureRecord createFromParcel(Parcel in) {
            return new PressureRecord(in);
        }

        @Override
        public PressureRecord[] newArray(int size) {
            return new PressureRecord[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "PressureRecord{" +
                "id=" + id +
                ", recordTime=" + recordTime +
                ", diastolic=" + diastolic +
                ", systolic=" + systolic +
                ", pulse=" + pulse +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(recordTime);
        dest.writeInt(diastolic);
        dest.writeInt(systolic);
        dest.writeInt(pulse);
    }
}
