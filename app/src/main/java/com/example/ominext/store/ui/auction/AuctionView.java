package com.example.ominext.store.ui.auction;

import com.example.ominext.store.model.UserAuction;

import java.util.List;

/**
 * Created by Ominext on 10/5/2017.
 */

public interface AuctionView {
    void getUserAuctionSuccessfully(List<UserAuction> userAuctionList);
    void getUserAuctionFailed(String s);
}
