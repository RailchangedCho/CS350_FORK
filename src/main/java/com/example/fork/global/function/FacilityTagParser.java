package com.example.fork.global.function;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FacilityTagParser {

    public static List<String> parseFacilityTag(Integer facilityTag) {

        List<String> hashTagList = new ArrayList<>();

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Korean");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Japanese");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Italian");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Mexican");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#French");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Indian");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Chinese");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Thai");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#American");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Spanish");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Greek");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Vietnamese");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Turkish");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Brazilian");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#German");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Moroccan");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Lebanese");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Ethiopian");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Russian");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Peruvian");
            }
            facilityTag = facilityTag >> 1;
        }

        if (facilityTag != 0) {
            if ((facilityTag & 1) == 1) {
                hashTagList.add("#Vegan");
            }
            facilityTag = facilityTag >> 1;
        }

        return hashTagList;
    }
}
