package ru.akvine.dbvisor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.managers.ScriptGeneratorServicesManager;
import ru.akvine.dbvisor.managers.TypeConverterServicesManager;
import ru.akvine.dbvisor.managers.UrlBuildersManager;
import ru.akvine.dbvisor.services.ScriptGeneratorService;
import ru.akvine.dbvisor.services.TypeConverterService;
import ru.akvine.dbvisor.services.database.url.URLBuilder;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Configuration
@RequiredArgsConstructor
public class ManagersConfig {

    @Bean
    public UrlBuildersManager urlBuildersManager(List<URLBuilder> builders) {
        Map<DatabaseType, URLBuilder> urlBuilderMap = builders
                .stream()
                .collect(toMap(URLBuilder::getType, identity()));
        return new UrlBuildersManager(urlBuilderMap);
    }

    @Bean
    public TypeConverterServicesManager typeConverterServicesManager(List<TypeConverterService> typeConverterServices) {
        Map<DatabaseType, TypeConverterService> urlBuilderMap = typeConverterServices
                .stream()
                .collect(toMap(TypeConverterService::getByDatabaseType, identity()));
        return new TypeConverterServicesManager(urlBuilderMap);
    }

    @Bean
    public ScriptGeneratorServicesManager scriptGeneratorServicesManager(List<ScriptGeneratorService> generators) {
        Map<DatabaseType, ScriptGeneratorService> scriptGenerators = generators.stream()
                .collect(toMap(ScriptGeneratorService::getDatabaseType, identity()));
        return new ScriptGeneratorServicesManager(scriptGenerators);
    }
}
