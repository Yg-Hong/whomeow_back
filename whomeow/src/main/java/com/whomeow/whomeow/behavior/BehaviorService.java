package com.whomeow.whomeow.behavior;

import com.whomeow.whomeow.profile.Dog;
import com.whomeow.whomeow.profile.ProfileJpaRepository;
import com.whomeow.whomeow.profile.ProfileService;
import com.whomeow.whomeow.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BehaviorService {

    private final BehaviorJpaRepository behaviorJpaRepository;
    private final ProfileJpaRepository profileJpaRepository;

    HashMap<String, Object> getWeeklyBehavior(User user) {
        HashMap<String,Object> map = new HashMap<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        String startDate = format.format(cal);
        map.put("startDate", startDate);

        Date date = new Date();
        String endDate = format.format(date);
        map.put("endDate", endDate);

        Optional<Dog> dog = profileJpaRepository.findByUser(user);
        if (dog.isEmpty()) {
            map.put("dogPresentFlag", false);
            HashMap<String, Integer> behavior = new HashMap<>();

            behavior.put("scratch", 0);
            behavior.put("turn", 0);
            behavior.put("feetup", 0);

            map.put("behavior", behavior);

            return map;
        } else {
            map.put("dogPresentFlag", true);

            List<Behavior> behaviors = behaviorJpaRepository.findByDate(startDate, endDate, dog.get().getDogKey());
            int turn = 0;
            int scratch = 0;
            int feetup = 0;
            for (Behavior behavior : behaviors) {
                if (behavior.getBehavior().equals("turn")) turn++;
                if (behavior.getBehavior().equals("scratch")) scratch++;
                if (behavior.getBehavior().equals("feetup")) feetup++;
            }
            HashMap<String, Integer> behavior = new HashMap<>();

            behavior.put("scratch", scratch);
            behavior.put("turn", turn);
            behavior.put("feetup", feetup);

            map.put("behavior", behavior);

            return map;
        }
    }
}
