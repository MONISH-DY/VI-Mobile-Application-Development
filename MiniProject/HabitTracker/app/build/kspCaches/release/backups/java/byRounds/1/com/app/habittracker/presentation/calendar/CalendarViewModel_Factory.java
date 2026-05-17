package com.app.habittracker.presentation.calendar;

import com.app.habittracker.domain.repository.HabitRepository;
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
public final class CalendarViewModel_Factory implements Factory<CalendarViewModel> {
  private final Provider<HabitRepository> habitRepositoryProvider;

  public CalendarViewModel_Factory(Provider<HabitRepository> habitRepositoryProvider) {
    this.habitRepositoryProvider = habitRepositoryProvider;
  }

  @Override
  public CalendarViewModel get() {
    return newInstance(habitRepositoryProvider.get());
  }

  public static CalendarViewModel_Factory create(
      Provider<HabitRepository> habitRepositoryProvider) {
    return new CalendarViewModel_Factory(habitRepositoryProvider);
  }

  public static CalendarViewModel newInstance(HabitRepository habitRepository) {
    return new CalendarViewModel(habitRepository);
  }
}
