package com.rahulrvr.cksample.retrofit.interfaces.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Copyright (c) 2015 Elsevier, Inc. All rights reserved.
 */
public class PathChoice implements Parcelable {

    @Expose
    private String pathChoiceNumber;
    @Expose
    private String description;

    /**
     *
     * @return
     * The pathChoiceNumber
     */
    public String getPathChoiceNumber() {
        return pathChoiceNumber;
    }

    /**
     *
     * @param pathChoiceNumber
     * The pathChoiceNumber
     */
    public void setPathChoiceNumber(String pathChoiceNumber) {
        this.pathChoiceNumber = pathChoiceNumber;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeString(pathChoiceNumber);
        dest.writeString(description);
    }

    public static final Parcelable.Creator<PathChoice> CREATOR
            = new Parcelable.Creator<PathChoice>() {
        public PathChoice createFromParcel(Parcel in) {
            return new PathChoice(in);
        }

        public PathChoice[] newArray(int size) {
            return new PathChoice[size];
        }
    };



    private PathChoice(Parcel in) {
        pathChoiceNumber = in.readString();
        description = in.readString();
    }
}