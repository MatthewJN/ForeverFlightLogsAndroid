package com.foreverflightlogs.foreverflightlogs;

public interface Syncable {

    /**
     * syncData() must be implemented.
     * @return If the sync was a success or not.
     */
    public boolean syncData();
}
