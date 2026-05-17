package com.app.habittracker.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.app.habittracker.data.local.dao.AchievementDao;
import com.app.habittracker.data.local.dao.AchievementDao_Impl;
import com.app.habittracker.data.local.dao.BossBattleDao;
import com.app.habittracker.data.local.dao.BossBattleDao_Impl;
import com.app.habittracker.data.local.dao.HabitDao;
import com.app.habittracker.data.local.dao.HabitDao_Impl;
import com.app.habittracker.data.local.dao.HabitHistoryDao;
import com.app.habittracker.data.local.dao.HabitHistoryDao_Impl;
import com.app.habittracker.data.local.dao.QuestDao;
import com.app.habittracker.data.local.dao.QuestDao_Impl;
import com.app.habittracker.data.local.dao.ReminderDao;
import com.app.habittracker.data.local.dao.ReminderDao_Impl;
import com.app.habittracker.data.local.dao.UserDao;
import com.app.habittracker.data.local.dao.UserDao_Impl;
import com.app.habittracker.data.local.dao.XPHistoryDao;
import com.app.habittracker.data.local.dao.XPHistoryDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class HabitDatabase_Impl extends HabitDatabase {
  private volatile HabitDao _habitDao;

  private volatile UserDao _userDao;

  private volatile QuestDao _questDao;

  private volatile XPHistoryDao _xPHistoryDao;

  private volatile AchievementDao _achievementDao;

  private volatile BossBattleDao _bossBattleDao;

  private volatile ReminderDao _reminderDao;

  private volatile HabitHistoryDao _habitHistoryDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(11) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` TEXT NOT NULL, `phoneNumber` TEXT NOT NULL, `pin` TEXT NOT NULL, `name` TEXT NOT NULL, `totalXp` INTEGER NOT NULL, `currentLevel` INTEGER NOT NULL, `currentStreak` INTEGER NOT NULL, `highestStreak` INTEGER NOT NULL, `difficultyPreference` TEXT NOT NULL, `reminderTime` TEXT NOT NULL, `lastHabitCompletionTime` INTEGER NOT NULL, `activeXpBoostUntil` INTEGER NOT NULL, `currentSparkySkin` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `habits` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `title` TEXT NOT NULL, `icon` TEXT NOT NULL, `category` TEXT NOT NULL, `difficulty` TEXT NOT NULL, `frequency` TEXT NOT NULL, `preferredTime` TEXT NOT NULL, `startTime` TEXT NOT NULL, `endTime` TEXT NOT NULL, `repeatDays` TEXT NOT NULL, `targetDuration` INTEGER NOT NULL, `notes` TEXT NOT NULL, `isCompletedToday` INTEGER NOT NULL, `isMissedToday` INTEGER NOT NULL, `streak` INTEGER NOT NULL, `isMastered` INTEGER NOT NULL, `lastNotificationDate` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_habits_isCompletedToday` ON `habits` (`isCompletedToday`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_habits_isMissedToday` ON `habits` (`isMissedToday`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `quests` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `xpReward` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `isRecovery` INTEGER NOT NULL, `lostStreak` INTEGER NOT NULL, `startTime` TEXT NOT NULL, `endTime` TEXT NOT NULL, `targetDuration` INTEGER NOT NULL, `isMissed` INTEGER NOT NULL, `isIncremental` INTEGER NOT NULL, `currentProgress` INTEGER NOT NULL, `targetProgress` INTEGER NOT NULL, `generatedDate` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `xp_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `amount` INTEGER NOT NULL, `reason` TEXT NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `achievements` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `icon` TEXT NOT NULL, `isUnlocked` INTEGER NOT NULL, `unlockedAt` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `boss_battles` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `task1Name` TEXT NOT NULL, `task1Desc` TEXT NOT NULL, `task1Completed` INTEGER NOT NULL, `task1StartTime` TEXT NOT NULL, `task1EndTime` TEXT NOT NULL, `task1Duration` INTEGER NOT NULL, `task2Name` TEXT NOT NULL, `task2Desc` TEXT NOT NULL, `task2Completed` INTEGER NOT NULL, `task2StartTime` TEXT NOT NULL, `task2EndTime` TEXT NOT NULL, `task2Duration` INTEGER NOT NULL, `task3Name` TEXT NOT NULL, `task3Desc` TEXT NOT NULL, `task3Completed` INTEGER NOT NULL, `task3StartTime` TEXT NOT NULL, `task3EndTime` TEXT NOT NULL, `task3Duration` INTEGER NOT NULL, `currentHp` INTEGER NOT NULL, `maxHp` INTEGER NOT NULL, `startDate` INTEGER NOT NULL, `endDate` INTEGER NOT NULL, `isDefeated` INTEGER NOT NULL, `isEnraged` INTEGER NOT NULL, `rewardXp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reminders` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `title` TEXT NOT NULL, `message` TEXT NOT NULL, `scheduledTime` INTEGER NOT NULL, `type` TEXT NOT NULL, `isSent` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `habit_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `habitId` INTEGER NOT NULL, `dateStr` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_habit_history_habitId` ON `habit_history` (`habitId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_habit_history_dateStr` ON `habit_history` (`dateStr`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '137d292f55915f43c91c782ea41753e4')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `habits`");
        db.execSQL("DROP TABLE IF EXISTS `quests`");
        db.execSQL("DROP TABLE IF EXISTS `xp_history`");
        db.execSQL("DROP TABLE IF EXISTS `achievements`");
        db.execSQL("DROP TABLE IF EXISTS `boss_battles`");
        db.execSQL("DROP TABLE IF EXISTS `reminders`");
        db.execSQL("DROP TABLE IF EXISTS `habit_history`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(13);
        _columnsUsers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("pin", new TableInfo.Column("pin", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("totalXp", new TableInfo.Column("totalXp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("currentLevel", new TableInfo.Column("currentLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("currentStreak", new TableInfo.Column("currentStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("highestStreak", new TableInfo.Column("highestStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("difficultyPreference", new TableInfo.Column("difficultyPreference", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("reminderTime", new TableInfo.Column("reminderTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("lastHabitCompletionTime", new TableInfo.Column("lastHabitCompletionTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("activeXpBoostUntil", new TableInfo.Column("activeXpBoostUntil", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("currentSparkySkin", new TableInfo.Column("currentSparkySkin", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.app.habittracker.data.local.entities.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsHabits = new HashMap<String, TableInfo.Column>(19);
        _columnsHabits.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("icon", new TableInfo.Column("icon", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("difficulty", new TableInfo.Column("difficulty", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("frequency", new TableInfo.Column("frequency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("preferredTime", new TableInfo.Column("preferredTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("startTime", new TableInfo.Column("startTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("endTime", new TableInfo.Column("endTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("repeatDays", new TableInfo.Column("repeatDays", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("targetDuration", new TableInfo.Column("targetDuration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("isCompletedToday", new TableInfo.Column("isCompletedToday", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("isMissedToday", new TableInfo.Column("isMissedToday", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("streak", new TableInfo.Column("streak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("isMastered", new TableInfo.Column("isMastered", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("lastNotificationDate", new TableInfo.Column("lastNotificationDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHabits = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHabits = new HashSet<TableInfo.Index>(2);
        _indicesHabits.add(new TableInfo.Index("index_habits_isCompletedToday", false, Arrays.asList("isCompletedToday"), Arrays.asList("ASC")));
        _indicesHabits.add(new TableInfo.Index("index_habits_isMissedToday", false, Arrays.asList("isMissedToday"), Arrays.asList("ASC")));
        final TableInfo _infoHabits = new TableInfo("habits", _columnsHabits, _foreignKeysHabits, _indicesHabits);
        final TableInfo _existingHabits = TableInfo.read(db, "habits");
        if (!_infoHabits.equals(_existingHabits)) {
          return new RoomOpenHelper.ValidationResult(false, "habits(com.app.habittracker.data.local.entities.HabitEntity).\n"
                  + " Expected:\n" + _infoHabits + "\n"
                  + " Found:\n" + _existingHabits);
        }
        final HashMap<String, TableInfo.Column> _columnsQuests = new HashMap<String, TableInfo.Column>(16);
        _columnsQuests.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("xpReward", new TableInfo.Column("xpReward", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("isRecovery", new TableInfo.Column("isRecovery", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("lostStreak", new TableInfo.Column("lostStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("startTime", new TableInfo.Column("startTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("endTime", new TableInfo.Column("endTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("targetDuration", new TableInfo.Column("targetDuration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("isMissed", new TableInfo.Column("isMissed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("isIncremental", new TableInfo.Column("isIncremental", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("currentProgress", new TableInfo.Column("currentProgress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("targetProgress", new TableInfo.Column("targetProgress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("generatedDate", new TableInfo.Column("generatedDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuests = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuests = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuests = new TableInfo("quests", _columnsQuests, _foreignKeysQuests, _indicesQuests);
        final TableInfo _existingQuests = TableInfo.read(db, "quests");
        if (!_infoQuests.equals(_existingQuests)) {
          return new RoomOpenHelper.ValidationResult(false, "quests(com.app.habittracker.data.local.entities.QuestEntity).\n"
                  + " Expected:\n" + _infoQuests + "\n"
                  + " Found:\n" + _existingQuests);
        }
        final HashMap<String, TableInfo.Column> _columnsXpHistory = new HashMap<String, TableInfo.Column>(5);
        _columnsXpHistory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsXpHistory.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsXpHistory.put("amount", new TableInfo.Column("amount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsXpHistory.put("reason", new TableInfo.Column("reason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsXpHistory.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysXpHistory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesXpHistory = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoXpHistory = new TableInfo("xp_history", _columnsXpHistory, _foreignKeysXpHistory, _indicesXpHistory);
        final TableInfo _existingXpHistory = TableInfo.read(db, "xp_history");
        if (!_infoXpHistory.equals(_existingXpHistory)) {
          return new RoomOpenHelper.ValidationResult(false, "xp_history(com.app.habittracker.data.local.entities.XPHistoryEntity).\n"
                  + " Expected:\n" + _infoXpHistory + "\n"
                  + " Found:\n" + _existingXpHistory);
        }
        final HashMap<String, TableInfo.Column> _columnsAchievements = new HashMap<String, TableInfo.Column>(7);
        _columnsAchievements.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("icon", new TableInfo.Column("icon", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("isUnlocked", new TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("unlockedAt", new TableInfo.Column("unlockedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAchievements = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAchievements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAchievements = new TableInfo("achievements", _columnsAchievements, _foreignKeysAchievements, _indicesAchievements);
        final TableInfo _existingAchievements = TableInfo.read(db, "achievements");
        if (!_infoAchievements.equals(_existingAchievements)) {
          return new RoomOpenHelper.ValidationResult(false, "achievements(com.app.habittracker.data.local.entities.AchievementEntity).\n"
                  + " Expected:\n" + _infoAchievements + "\n"
                  + " Found:\n" + _existingAchievements);
        }
        final HashMap<String, TableInfo.Column> _columnsBossBattles = new HashMap<String, TableInfo.Column>(29);
        _columnsBossBattles.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task1Name", new TableInfo.Column("task1Name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task1Desc", new TableInfo.Column("task1Desc", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task1Completed", new TableInfo.Column("task1Completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task1StartTime", new TableInfo.Column("task1StartTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task1EndTime", new TableInfo.Column("task1EndTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task1Duration", new TableInfo.Column("task1Duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task2Name", new TableInfo.Column("task2Name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task2Desc", new TableInfo.Column("task2Desc", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task2Completed", new TableInfo.Column("task2Completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task2StartTime", new TableInfo.Column("task2StartTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task2EndTime", new TableInfo.Column("task2EndTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task2Duration", new TableInfo.Column("task2Duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task3Name", new TableInfo.Column("task3Name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task3Desc", new TableInfo.Column("task3Desc", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task3Completed", new TableInfo.Column("task3Completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task3StartTime", new TableInfo.Column("task3StartTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task3EndTime", new TableInfo.Column("task3EndTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("task3Duration", new TableInfo.Column("task3Duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("currentHp", new TableInfo.Column("currentHp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("maxHp", new TableInfo.Column("maxHp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("endDate", new TableInfo.Column("endDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("isDefeated", new TableInfo.Column("isDefeated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("isEnraged", new TableInfo.Column("isEnraged", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBossBattles.put("rewardXp", new TableInfo.Column("rewardXp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBossBattles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBossBattles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBossBattles = new TableInfo("boss_battles", _columnsBossBattles, _foreignKeysBossBattles, _indicesBossBattles);
        final TableInfo _existingBossBattles = TableInfo.read(db, "boss_battles");
        if (!_infoBossBattles.equals(_existingBossBattles)) {
          return new RoomOpenHelper.ValidationResult(false, "boss_battles(com.app.habittracker.data.local.entities.BossBattleEntity).\n"
                  + " Expected:\n" + _infoBossBattles + "\n"
                  + " Found:\n" + _existingBossBattles);
        }
        final HashMap<String, TableInfo.Column> _columnsReminders = new HashMap<String, TableInfo.Column>(7);
        _columnsReminders.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("message", new TableInfo.Column("message", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("scheduledTime", new TableInfo.Column("scheduledTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("isSent", new TableInfo.Column("isSent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReminders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReminders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReminders = new TableInfo("reminders", _columnsReminders, _foreignKeysReminders, _indicesReminders);
        final TableInfo _existingReminders = TableInfo.read(db, "reminders");
        if (!_infoReminders.equals(_existingReminders)) {
          return new RoomOpenHelper.ValidationResult(false, "reminders(com.app.habittracker.data.local.entities.ReminderEntity).\n"
                  + " Expected:\n" + _infoReminders + "\n"
                  + " Found:\n" + _existingReminders);
        }
        final HashMap<String, TableInfo.Column> _columnsHabitHistory = new HashMap<String, TableInfo.Column>(5);
        _columnsHabitHistory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabitHistory.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabitHistory.put("habitId", new TableInfo.Column("habitId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabitHistory.put("dateStr", new TableInfo.Column("dateStr", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabitHistory.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHabitHistory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHabitHistory = new HashSet<TableInfo.Index>(2);
        _indicesHabitHistory.add(new TableInfo.Index("index_habit_history_habitId", false, Arrays.asList("habitId"), Arrays.asList("ASC")));
        _indicesHabitHistory.add(new TableInfo.Index("index_habit_history_dateStr", false, Arrays.asList("dateStr"), Arrays.asList("ASC")));
        final TableInfo _infoHabitHistory = new TableInfo("habit_history", _columnsHabitHistory, _foreignKeysHabitHistory, _indicesHabitHistory);
        final TableInfo _existingHabitHistory = TableInfo.read(db, "habit_history");
        if (!_infoHabitHistory.equals(_existingHabitHistory)) {
          return new RoomOpenHelper.ValidationResult(false, "habit_history(com.app.habittracker.data.local.entities.HabitHistoryEntity).\n"
                  + " Expected:\n" + _infoHabitHistory + "\n"
                  + " Found:\n" + _existingHabitHistory);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "137d292f55915f43c91c782ea41753e4", "a4db4445daf3dff11f1512cacd388c1a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","habits","quests","xp_history","achievements","boss_battles","reminders","habit_history");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `habits`");
      _db.execSQL("DELETE FROM `quests`");
      _db.execSQL("DELETE FROM `xp_history`");
      _db.execSQL("DELETE FROM `achievements`");
      _db.execSQL("DELETE FROM `boss_battles`");
      _db.execSQL("DELETE FROM `reminders`");
      _db.execSQL("DELETE FROM `habit_history`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(HabitDao.class, HabitDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(QuestDao.class, QuestDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(XPHistoryDao.class, XPHistoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AchievementDao.class, AchievementDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BossBattleDao.class, BossBattleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReminderDao.class, ReminderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(HabitHistoryDao.class, HabitHistoryDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public HabitDao getHabitDao() {
    if (_habitDao != null) {
      return _habitDao;
    } else {
      synchronized(this) {
        if(_habitDao == null) {
          _habitDao = new HabitDao_Impl(this);
        }
        return _habitDao;
      }
    }
  }

  @Override
  public UserDao getUserDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public QuestDao getQuestDao() {
    if (_questDao != null) {
      return _questDao;
    } else {
      synchronized(this) {
        if(_questDao == null) {
          _questDao = new QuestDao_Impl(this);
        }
        return _questDao;
      }
    }
  }

  @Override
  public XPHistoryDao getXpHistoryDao() {
    if (_xPHistoryDao != null) {
      return _xPHistoryDao;
    } else {
      synchronized(this) {
        if(_xPHistoryDao == null) {
          _xPHistoryDao = new XPHistoryDao_Impl(this);
        }
        return _xPHistoryDao;
      }
    }
  }

  @Override
  public AchievementDao getAchievementDao() {
    if (_achievementDao != null) {
      return _achievementDao;
    } else {
      synchronized(this) {
        if(_achievementDao == null) {
          _achievementDao = new AchievementDao_Impl(this);
        }
        return _achievementDao;
      }
    }
  }

  @Override
  public BossBattleDao getBossBattleDao() {
    if (_bossBattleDao != null) {
      return _bossBattleDao;
    } else {
      synchronized(this) {
        if(_bossBattleDao == null) {
          _bossBattleDao = new BossBattleDao_Impl(this);
        }
        return _bossBattleDao;
      }
    }
  }

  @Override
  public ReminderDao getReminderDao() {
    if (_reminderDao != null) {
      return _reminderDao;
    } else {
      synchronized(this) {
        if(_reminderDao == null) {
          _reminderDao = new ReminderDao_Impl(this);
        }
        return _reminderDao;
      }
    }
  }

  @Override
  public HabitHistoryDao getHabitHistoryDao() {
    if (_habitHistoryDao != null) {
      return _habitHistoryDao;
    } else {
      synchronized(this) {
        if(_habitHistoryDao == null) {
          _habitHistoryDao = new HabitHistoryDao_Impl(this);
        }
        return _habitHistoryDao;
      }
    }
  }
}
