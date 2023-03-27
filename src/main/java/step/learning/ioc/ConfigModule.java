package step.learning.ioc;

import com.google.inject.AbstractModule;
import jdk.jfr.Name;
import step.learning.hash.HashService;
import step.learning.hash.MD5HashService;
import step.learning.hash.Sha1HashService;
import step.learning.services.DataService;
import step.learning.services.EmailService;
import step.learning.services.GmailService;
import step.learning.services.MysqlDataService;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        // Конфигурация служб-поставщиков
        bind(DataService.class).to(MysqlDataService.class) ;
        bind(HashService.class).to(Sha1HashService.class) ;
        bind(EmailService.class).to(GmailService.class);
    }
}
