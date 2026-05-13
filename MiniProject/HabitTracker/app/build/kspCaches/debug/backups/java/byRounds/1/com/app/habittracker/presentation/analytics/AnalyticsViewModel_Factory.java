package com.app.habittracker.presentation.analytics;

import com.app.habittracker.domain.repository.UserRepository;
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
public final class AnalyticsViewModel_Factory implements Factory<AnalyticsViewModel> {
  private final Provider<UserRepository> userRepositoryProvider;

  public AnalyticsViewModel_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public AnalyticsViewModel get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static AnalyticsViewModel_Factory create(Provider<UserRepository> userRepositoryProvider) {
    return new AnalyticsViewModel_Factory(userRepositoryProvider);
  }

  public static AnalyticsViewModel newInstance(UserRepository userRepository) {
    return new AnalyticsViewModel(userRepository);
  }
}
