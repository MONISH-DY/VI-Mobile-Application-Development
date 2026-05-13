package com.app.habittracker.domain.engines;

import com.app.habittracker.data.local.PreferencesManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("com.app.habittracker.di.ApplicationScope")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class TimerManager_Factory implements Factory<TimerManager> {
  private final Provider<PreferencesManager> preferencesManagerProvider;

  private final Provider<CoroutineScope> scopeProvider;

  public TimerManager_Factory(Provider<PreferencesManager> preferencesManagerProvider,
      Provider<CoroutineScope> scopeProvider) {
    this.preferencesManagerProvider = preferencesManagerProvider;
    this.scopeProvider = scopeProvider;
  }

  @Override
  public TimerManager get() {
    return newInstance(preferencesManagerProvider.get(), scopeProvider.get());
  }

  public static TimerManager_Factory create(Provider<PreferencesManager> preferencesManagerProvider,
      Provider<CoroutineScope> scopeProvider) {
    return new TimerManager_Factory(preferencesManagerProvider, scopeProvider);
  }

  public static TimerManager newInstance(PreferencesManager preferencesManager,
      CoroutineScope scope) {
    return new TimerManager(preferencesManager, scope);
  }
}
