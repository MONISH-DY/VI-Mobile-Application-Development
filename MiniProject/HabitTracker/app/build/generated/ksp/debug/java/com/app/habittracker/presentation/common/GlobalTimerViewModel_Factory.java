package com.app.habittracker.presentation.common;

import com.app.habittracker.domain.engines.TimerManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class GlobalTimerViewModel_Factory implements Factory<GlobalTimerViewModel> {
  private final Provider<TimerManager> timerManagerProvider;

  public GlobalTimerViewModel_Factory(Provider<TimerManager> timerManagerProvider) {
    this.timerManagerProvider = timerManagerProvider;
  }

  @Override
  public GlobalTimerViewModel get() {
    return newInstance(timerManagerProvider.get());
  }

  public static GlobalTimerViewModel_Factory create(Provider<TimerManager> timerManagerProvider) {
    return new GlobalTimerViewModel_Factory(timerManagerProvider);
  }

  public static GlobalTimerViewModel newInstance(TimerManager timerManager) {
    return new GlobalTimerViewModel(timerManager);
  }
}
