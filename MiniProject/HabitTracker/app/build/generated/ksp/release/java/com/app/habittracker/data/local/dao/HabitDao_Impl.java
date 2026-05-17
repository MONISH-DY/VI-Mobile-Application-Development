package com.app.habittracker.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.app.habittracker.data.local.entities.HabitEntity;
import com.app.habittracker.data.local.entities.HabitHistoryEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class HabitDao_Impl implements HabitDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HabitEntity> __insertionAdapterOfHabitEntity;

  private final EntityInsertionAdapter<HabitHistoryEntity> __insertionAdapterOfHabitHistoryEntity;

  private final EntityDeletionOrUpdateAdapter<HabitEntity> __deletionAdapterOfHabitEntity;

  private final EntityDeletionOrUpdateAdapter<HabitEntity> __updateAdapterOfHabitEntity;

  private final SharedSQLiteStatement __preparedStmtOfResetMissedStreaks;

  private final SharedSQLiteStatement __preparedStmtOfResetDailyCompletions;

  private final SharedSQLiteStatement __preparedStmtOfIncrementAllStreaks;

  public HabitDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHabitEntity = new EntityInsertionAdapter<HabitEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `habits` (`id`,`userId`,`title`,`icon`,`category`,`difficulty`,`frequency`,`preferredTime`,`startTime`,`endTime`,`repeatDays`,`targetDuration`,`notes`,`isCompletedToday`,`isMissedToday`,`streak`,`isMastered`,`lastNotificationDate`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HabitEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getIcon());
        statement.bindString(5, entity.getCategory());
        statement.bindString(6, entity.getDifficulty());
        statement.bindString(7, entity.getFrequency());
        statement.bindString(8, entity.getPreferredTime());
        statement.bindString(9, entity.getStartTime());
        statement.bindString(10, entity.getEndTime());
        statement.bindString(11, entity.getRepeatDays());
        statement.bindLong(12, entity.getTargetDuration());
        statement.bindString(13, entity.getNotes());
        final int _tmp = entity.isCompletedToday() ? 1 : 0;
        statement.bindLong(14, _tmp);
        final int _tmp_1 = entity.isMissedToday() ? 1 : 0;
        statement.bindLong(15, _tmp_1);
        statement.bindLong(16, entity.getStreak());
        final int _tmp_2 = entity.isMastered() ? 1 : 0;
        statement.bindLong(17, _tmp_2);
        statement.bindLong(18, entity.getLastNotificationDate());
        statement.bindLong(19, entity.getCreatedAt());
      }
    };
    this.__insertionAdapterOfHabitHistoryEntity = new EntityInsertionAdapter<HabitHistoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `habit_history` (`id`,`userId`,`habitId`,`dateStr`,`isCompleted`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HabitHistoryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindLong(3, entity.getHabitId());
        statement.bindString(4, entity.getDateStr());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(5, _tmp);
      }
    };
    this.__deletionAdapterOfHabitEntity = new EntityDeletionOrUpdateAdapter<HabitEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `habits` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HabitEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfHabitEntity = new EntityDeletionOrUpdateAdapter<HabitEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `habits` SET `id` = ?,`userId` = ?,`title` = ?,`icon` = ?,`category` = ?,`difficulty` = ?,`frequency` = ?,`preferredTime` = ?,`startTime` = ?,`endTime` = ?,`repeatDays` = ?,`targetDuration` = ?,`notes` = ?,`isCompletedToday` = ?,`isMissedToday` = ?,`streak` = ?,`isMastered` = ?,`lastNotificationDate` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HabitEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getIcon());
        statement.bindString(5, entity.getCategory());
        statement.bindString(6, entity.getDifficulty());
        statement.bindString(7, entity.getFrequency());
        statement.bindString(8, entity.getPreferredTime());
        statement.bindString(9, entity.getStartTime());
        statement.bindString(10, entity.getEndTime());
        statement.bindString(11, entity.getRepeatDays());
        statement.bindLong(12, entity.getTargetDuration());
        statement.bindString(13, entity.getNotes());
        final int _tmp = entity.isCompletedToday() ? 1 : 0;
        statement.bindLong(14, _tmp);
        final int _tmp_1 = entity.isMissedToday() ? 1 : 0;
        statement.bindLong(15, _tmp_1);
        statement.bindLong(16, entity.getStreak());
        final int _tmp_2 = entity.isMastered() ? 1 : 0;
        statement.bindLong(17, _tmp_2);
        statement.bindLong(18, entity.getLastNotificationDate());
        statement.bindLong(19, entity.getCreatedAt());
        statement.bindLong(20, entity.getId());
      }
    };
    this.__preparedStmtOfResetMissedStreaks = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE habits SET streak = 0, isMissedToday = 1 WHERE userId = ? AND isCompletedToday = 0";
        return _query;
      }
    };
    this.__preparedStmtOfResetDailyCompletions = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE habits SET isCompletedToday = 0, isMissedToday = 0 WHERE userId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfIncrementAllStreaks = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE habits SET streak = streak + ? WHERE userId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertHabit(final HabitEntity habit, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfHabitEntity.insertAndReturnId(habit);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertHabitHistory(final HabitHistoryEntity history,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfHabitHistoryEntity.insert(history);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteHabit(final HabitEntity habit, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfHabitEntity.handle(habit);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateHabit(final HabitEntity habit, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfHabitEntity.handle(habit);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object logHabitCompletionAtomic(final String userId, final int habitId,
      final String dateStr, final boolean isCompleted, final boolean updateHabitStreak,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> HabitDao.DefaultImpls.logHabitCompletionAtomic(HabitDao_Impl.this, userId, habitId, dateStr, isCompleted, updateHabitStreak, __cont), $completion);
  }

  @Override
  public Object resetMissedStreaks(final String userId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfResetMissedStreaks.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, userId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfResetMissedStreaks.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object resetDailyCompletions(final String userId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfResetDailyCompletions.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, userId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfResetDailyCompletions.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object incrementAllStreaks(final String userId, final int amount,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementAllStreaks.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, amount);
        _argIndex = 2;
        _stmt.bindString(_argIndex, userId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfIncrementAllStreaks.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<HabitEntity>> getAllHabits(final String userId) {
    final String _sql = "SELECT * FROM habits WHERE userId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"habits"}, new Callable<List<HabitEntity>>() {
      @Override
      @NonNull
      public List<HabitEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfPreferredTime = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredTime");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfRepeatDays = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatDays");
          final int _cursorIndexOfTargetDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDuration");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompletedToday = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompletedToday");
          final int _cursorIndexOfIsMissedToday = CursorUtil.getColumnIndexOrThrow(_cursor, "isMissedToday");
          final int _cursorIndexOfStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "streak");
          final int _cursorIndexOfIsMastered = CursorUtil.getColumnIndexOrThrow(_cursor, "isMastered");
          final int _cursorIndexOfLastNotificationDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastNotificationDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<HabitEntity> _result = new ArrayList<HabitEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HabitEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpIcon;
            _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpDifficulty;
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final String _tmpPreferredTime;
            _tmpPreferredTime = _cursor.getString(_cursorIndexOfPreferredTime);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final String _tmpRepeatDays;
            _tmpRepeatDays = _cursor.getString(_cursorIndexOfRepeatDays);
            final int _tmpTargetDuration;
            _tmpTargetDuration = _cursor.getInt(_cursorIndexOfTargetDuration);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompletedToday;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompletedToday);
            _tmpIsCompletedToday = _tmp != 0;
            final boolean _tmpIsMissedToday;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsMissedToday);
            _tmpIsMissedToday = _tmp_1 != 0;
            final int _tmpStreak;
            _tmpStreak = _cursor.getInt(_cursorIndexOfStreak);
            final boolean _tmpIsMastered;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsMastered);
            _tmpIsMastered = _tmp_2 != 0;
            final long _tmpLastNotificationDate;
            _tmpLastNotificationDate = _cursor.getLong(_cursorIndexOfLastNotificationDate);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new HabitEntity(_tmpId,_tmpUserId,_tmpTitle,_tmpIcon,_tmpCategory,_tmpDifficulty,_tmpFrequency,_tmpPreferredTime,_tmpStartTime,_tmpEndTime,_tmpRepeatDays,_tmpTargetDuration,_tmpNotes,_tmpIsCompletedToday,_tmpIsMissedToday,_tmpStreak,_tmpIsMastered,_tmpLastNotificationDate,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<HabitEntity>> getPendingHabits(final String userId) {
    final String _sql = "SELECT * FROM habits WHERE userId = ? AND isCompletedToday = 0 AND isMissedToday = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"habits"}, new Callable<List<HabitEntity>>() {
      @Override
      @NonNull
      public List<HabitEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfPreferredTime = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredTime");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfRepeatDays = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatDays");
          final int _cursorIndexOfTargetDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDuration");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompletedToday = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompletedToday");
          final int _cursorIndexOfIsMissedToday = CursorUtil.getColumnIndexOrThrow(_cursor, "isMissedToday");
          final int _cursorIndexOfStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "streak");
          final int _cursorIndexOfIsMastered = CursorUtil.getColumnIndexOrThrow(_cursor, "isMastered");
          final int _cursorIndexOfLastNotificationDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastNotificationDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<HabitEntity> _result = new ArrayList<HabitEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HabitEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpIcon;
            _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpDifficulty;
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final String _tmpPreferredTime;
            _tmpPreferredTime = _cursor.getString(_cursorIndexOfPreferredTime);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final String _tmpRepeatDays;
            _tmpRepeatDays = _cursor.getString(_cursorIndexOfRepeatDays);
            final int _tmpTargetDuration;
            _tmpTargetDuration = _cursor.getInt(_cursorIndexOfTargetDuration);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompletedToday;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompletedToday);
            _tmpIsCompletedToday = _tmp != 0;
            final boolean _tmpIsMissedToday;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsMissedToday);
            _tmpIsMissedToday = _tmp_1 != 0;
            final int _tmpStreak;
            _tmpStreak = _cursor.getInt(_cursorIndexOfStreak);
            final boolean _tmpIsMastered;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsMastered);
            _tmpIsMastered = _tmp_2 != 0;
            final long _tmpLastNotificationDate;
            _tmpLastNotificationDate = _cursor.getLong(_cursorIndexOfLastNotificationDate);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new HabitEntity(_tmpId,_tmpUserId,_tmpTitle,_tmpIcon,_tmpCategory,_tmpDifficulty,_tmpFrequency,_tmpPreferredTime,_tmpStartTime,_tmpEndTime,_tmpRepeatDays,_tmpTargetDuration,_tmpNotes,_tmpIsCompletedToday,_tmpIsMissedToday,_tmpStreak,_tmpIsMastered,_tmpLastNotificationDate,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getHabitById(final int id, final Continuation<? super HabitEntity> $completion) {
    final String _sql = "SELECT * FROM habits WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HabitEntity>() {
      @Override
      @Nullable
      public HabitEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfPreferredTime = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredTime");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfRepeatDays = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatDays");
          final int _cursorIndexOfTargetDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDuration");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompletedToday = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompletedToday");
          final int _cursorIndexOfIsMissedToday = CursorUtil.getColumnIndexOrThrow(_cursor, "isMissedToday");
          final int _cursorIndexOfStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "streak");
          final int _cursorIndexOfIsMastered = CursorUtil.getColumnIndexOrThrow(_cursor, "isMastered");
          final int _cursorIndexOfLastNotificationDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastNotificationDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final HabitEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpIcon;
            _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpDifficulty;
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final String _tmpPreferredTime;
            _tmpPreferredTime = _cursor.getString(_cursorIndexOfPreferredTime);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final String _tmpRepeatDays;
            _tmpRepeatDays = _cursor.getString(_cursorIndexOfRepeatDays);
            final int _tmpTargetDuration;
            _tmpTargetDuration = _cursor.getInt(_cursorIndexOfTargetDuration);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompletedToday;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompletedToday);
            _tmpIsCompletedToday = _tmp != 0;
            final boolean _tmpIsMissedToday;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsMissedToday);
            _tmpIsMissedToday = _tmp_1 != 0;
            final int _tmpStreak;
            _tmpStreak = _cursor.getInt(_cursorIndexOfStreak);
            final boolean _tmpIsMastered;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsMastered);
            _tmpIsMastered = _tmp_2 != 0;
            final long _tmpLastNotificationDate;
            _tmpLastNotificationDate = _cursor.getLong(_cursorIndexOfLastNotificationDate);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new HabitEntity(_tmpId,_tmpUserId,_tmpTitle,_tmpIcon,_tmpCategory,_tmpDifficulty,_tmpFrequency,_tmpPreferredTime,_tmpStartTime,_tmpEndTime,_tmpRepeatDays,_tmpTargetDuration,_tmpNotes,_tmpIsCompletedToday,_tmpIsMissedToday,_tmpStreak,_tmpIsMastered,_tmpLastNotificationDate,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnprocessedHabitsForDay(final String userId, final String day,
      final Continuation<? super List<HabitEntity>> $completion) {
    final String _sql = "SELECT * FROM habits WHERE userId = ? AND repeatDays LIKE '%' || ? || '%' AND isCompletedToday = 0 AND isMissedToday = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    _statement.bindString(_argIndex, day);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<HabitEntity>>() {
      @Override
      @NonNull
      public List<HabitEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfPreferredTime = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredTime");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfRepeatDays = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatDays");
          final int _cursorIndexOfTargetDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDuration");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompletedToday = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompletedToday");
          final int _cursorIndexOfIsMissedToday = CursorUtil.getColumnIndexOrThrow(_cursor, "isMissedToday");
          final int _cursorIndexOfStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "streak");
          final int _cursorIndexOfIsMastered = CursorUtil.getColumnIndexOrThrow(_cursor, "isMastered");
          final int _cursorIndexOfLastNotificationDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastNotificationDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<HabitEntity> _result = new ArrayList<HabitEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HabitEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpIcon;
            _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpDifficulty;
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final String _tmpPreferredTime;
            _tmpPreferredTime = _cursor.getString(_cursorIndexOfPreferredTime);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final String _tmpRepeatDays;
            _tmpRepeatDays = _cursor.getString(_cursorIndexOfRepeatDays);
            final int _tmpTargetDuration;
            _tmpTargetDuration = _cursor.getInt(_cursorIndexOfTargetDuration);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompletedToday;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompletedToday);
            _tmpIsCompletedToday = _tmp != 0;
            final boolean _tmpIsMissedToday;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsMissedToday);
            _tmpIsMissedToday = _tmp_1 != 0;
            final int _tmpStreak;
            _tmpStreak = _cursor.getInt(_cursorIndexOfStreak);
            final boolean _tmpIsMastered;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsMastered);
            _tmpIsMastered = _tmp_2 != 0;
            final long _tmpLastNotificationDate;
            _tmpLastNotificationDate = _cursor.getLong(_cursorIndexOfLastNotificationDate);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new HabitEntity(_tmpId,_tmpUserId,_tmpTitle,_tmpIcon,_tmpCategory,_tmpDifficulty,_tmpFrequency,_tmpPreferredTime,_tmpStartTime,_tmpEndTime,_tmpRepeatDays,_tmpTargetDuration,_tmpNotes,_tmpIsCompletedToday,_tmpIsMissedToday,_tmpStreak,_tmpIsMastered,_tmpLastNotificationDate,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getMissedHabits(final String userId,
      final Continuation<? super List<HabitEntity>> $completion) {
    final String _sql = "SELECT * FROM habits WHERE userId = ? AND isMissedToday = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<HabitEntity>>() {
      @Override
      @NonNull
      public List<HabitEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfPreferredTime = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredTime");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfRepeatDays = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatDays");
          final int _cursorIndexOfTargetDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDuration");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsCompletedToday = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompletedToday");
          final int _cursorIndexOfIsMissedToday = CursorUtil.getColumnIndexOrThrow(_cursor, "isMissedToday");
          final int _cursorIndexOfStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "streak");
          final int _cursorIndexOfIsMastered = CursorUtil.getColumnIndexOrThrow(_cursor, "isMastered");
          final int _cursorIndexOfLastNotificationDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastNotificationDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<HabitEntity> _result = new ArrayList<HabitEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HabitEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpIcon;
            _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpDifficulty;
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final String _tmpPreferredTime;
            _tmpPreferredTime = _cursor.getString(_cursorIndexOfPreferredTime);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final String _tmpRepeatDays;
            _tmpRepeatDays = _cursor.getString(_cursorIndexOfRepeatDays);
            final int _tmpTargetDuration;
            _tmpTargetDuration = _cursor.getInt(_cursorIndexOfTargetDuration);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsCompletedToday;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompletedToday);
            _tmpIsCompletedToday = _tmp != 0;
            final boolean _tmpIsMissedToday;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsMissedToday);
            _tmpIsMissedToday = _tmp_1 != 0;
            final int _tmpStreak;
            _tmpStreak = _cursor.getInt(_cursorIndexOfStreak);
            final boolean _tmpIsMastered;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsMastered);
            _tmpIsMastered = _tmp_2 != 0;
            final long _tmpLastNotificationDate;
            _tmpLastNotificationDate = _cursor.getLong(_cursorIndexOfLastNotificationDate);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new HabitEntity(_tmpId,_tmpUserId,_tmpTitle,_tmpIcon,_tmpCategory,_tmpDifficulty,_tmpFrequency,_tmpPreferredTime,_tmpStartTime,_tmpEndTime,_tmpRepeatDays,_tmpTargetDuration,_tmpNotes,_tmpIsCompletedToday,_tmpIsMissedToday,_tmpStreak,_tmpIsMastered,_tmpLastNotificationDate,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getHistoryRecord(final String userId, final String dateStr, final int habitId,
      final Continuation<? super HabitHistoryEntity> $completion) {
    final String _sql = "SELECT * FROM habit_history WHERE userId = ? AND dateStr = ? AND habitId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    _statement.bindString(_argIndex, dateStr);
    _argIndex = 3;
    _statement.bindLong(_argIndex, habitId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HabitHistoryEntity>() {
      @Override
      @Nullable
      public HabitHistoryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfHabitId = CursorUtil.getColumnIndexOrThrow(_cursor, "habitId");
          final int _cursorIndexOfDateStr = CursorUtil.getColumnIndexOrThrow(_cursor, "dateStr");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final HabitHistoryEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final int _tmpHabitId;
            _tmpHabitId = _cursor.getInt(_cursorIndexOfHabitId);
            final String _tmpDateStr;
            _tmpDateStr = _cursor.getString(_cursorIndexOfDateStr);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            _result = new HabitHistoryEntity(_tmpId,_tmpUserId,_tmpHabitId,_tmpDateStr,_tmpIsCompleted);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
