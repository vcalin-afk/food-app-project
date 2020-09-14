package ro.calin.FoodApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.calin.FoodApp.database.RetetePracticePage;
import ro.calin.FoodApp.database.RetetePracticePageDao;

@Service
public class RetetePracticePageService {

    @Autowired
    private RetetePracticePageDao retetePracticePageDao;

    public Iterable<RetetePracticePage> findAll() {
        return retetePracticePageDao.findAll();
    }

}
