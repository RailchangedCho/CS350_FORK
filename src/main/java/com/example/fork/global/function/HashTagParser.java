package com.example.fork.global.function;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HashTagParser {

    public static List<String> parseHashTag(Integer hashtag) {

        List<String> hashTagList = new ArrayList<>();

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#Cozy");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#Elegant");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#Spacious");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#Accessible");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#OutdoorSeating");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#FamilyFriendly");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#PetFriendly");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#WiFiEnabled");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#PrivateDining");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#WheelchairAccessible");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#Spicy");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#Delicious");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#Authentic");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#Healthy");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#Vegetarian");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#Vegan");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#GlutenFree");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#FarmtoTable");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#Gourmet");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#Fusion");
            }
            hashtag = hashtag >> 1;
        }

        if (hashtag != 0) {
            if ((hashtag & 1) == 1) {
                hashTagList.add("#StreetFood");
            }
            hashtag = hashtag >> 1;
        }

        return hashTagList;
    }
}
