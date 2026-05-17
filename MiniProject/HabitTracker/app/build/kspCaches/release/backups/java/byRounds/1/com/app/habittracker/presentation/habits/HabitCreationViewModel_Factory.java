package com.app.habittracker.presentation.habits;

import android.content.Context;
import com.app.habittracker.domain.repository.HabitRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class HabitCreationViewModel_Factory implements Factory<HabitCreationViewModel> {
  private final Provider<HabitRepository> habitRepositoryProvider;

  private final Provider<Context> contextProvider;

  public HabitCreationViewModel_Factory(Provider<HabitRepository> habitRepositoryProvider,
      Provider<Context> contextProvider) {
    this.habitRepositoryProvider = habitRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public HabitCreationViewModel get() {
    return newInstance(habitRepositoryProvider.get(), contextProvider.get());
  }

  public static HabitCreationViewModel_Factory create(
      Provider<HabitRepository> habitRepositoryProvider, Provider<Context> contextProvider) {
    return new HabitCreationViewModel_Factory(habitRepositoryProvider, contextProvider);
  }

  public static HabitCreationViewModel newInstance(HabitRepository habitRepository,
      Context context) {
    return new HabitCreationViewModel(habitRepository, context);
  }
}
