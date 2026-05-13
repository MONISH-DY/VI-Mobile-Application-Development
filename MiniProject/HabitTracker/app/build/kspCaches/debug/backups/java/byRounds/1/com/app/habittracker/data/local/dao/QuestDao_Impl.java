package com.app.habittracker.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.app.habittracker.data.local.entities.QuestEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class QuestDao_Impl implements QuestDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QuestEntity> __insertionAdapterOfQuestEntity;

  private final EntityDeletionOrUpdateAdapter<QuestEntity> __updateAdapterOfQuestEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteQuestByTitle;

  private final SharedSQLiteStatement __preparedStmtOfClearAllQuests;

  public QuestDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuestEntity = new EntityInsertionAdapter<QuestEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `quests` (`id`,`userId`,`title`,`description`,`xpReward`,`isCompleted`,`isRecovery`,`lostStreak`,`startTime`,`endTime`,`targetDuration`,`isMissed`,`isIncremental`,`currentProgress`,`targetProgress`,`generatedDate`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QuestEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getDescription());
        statement.bindLong(5, entity.getXpReward());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(6, _tmp);
        final int _tmp_1 = entity.isRecovery() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        statement.bindLong(8, entity.getLostStreak());
        statement.bindString(9, entity.getStartTime());
        statement.bindString(10, entity.getEndTime());
        statement.bindLong(11, entity.getTargetDuration());
        final int _tmp_2 = entity.isMissed() ? 1 : 0;
        statement.bindLong(12, _tmp_2);
        final int _tmp_3 = entity.isIncremental() ? 1 : 0;
        statement.bindLong(13, _tmp_3);
        statement.bindLong(14, entity.getCurrentProgress());
        statement.bindLong(15, entity.getTargetProgress());
        statement.bindLong(16, entity.getGeneratedDate());
      }
    };
    this.__updateAdapterOfQuestEntity = new EntityDeletionOrUpdateAdapter<QuestEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `quests` SET `id` = ?,`userId` = ?,`title` = ?,`description` = ?,`xpReward` = ?,`isCompleted` = ?,`isRecovery` = ?,`lostStreak` = ?,`startTime` = ?,`endTime` = ?,`targetDuration` = ?,`isMissed` = ?,`isIncremental` = ?,`currentProgress` = ?,`targetProgress` = ?,`generatedDate` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QuestEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getDescription());
        statement.bindLong(5, entity.getXpReward());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(6, _tmp);
        final int _tmp_1 = entity.isRecovery() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        statement.bindLong(8, entity.getLostStreak());
        statement.bindString(9, entity.getStartTime());
        statement.bindString(10, entity.getEndTime());
        statement.bindLong(11, entity.getTargetDuration());
        final int _tmp_2 = entity.isMissed() ? 1 : 0;
        statement.bindLong(12, _tmp_2);
        final int _tmp_3 = entity.isIncremental() ? 1 : 0;
        statement.bindLong(13, _tmp_3);
        statement.bindLong(14, entity.getCurrentProgress());
        statement.bindLong(15, entity.getTargetProgress());
        statement.bindLong(16, entity.getGeneratedDate());
        statement.bindLong(17, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteQuestByTitle = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM quests WHERE userId = ? AND title = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearAllQuests = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM quests WHERE userId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertQuest(final QuestEntity quest, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQuestEntity.insert(quest);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateQuest(final QuestEntity quest, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfQuestEntity.handle(quest);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteQuestByTitle(final String userId, final String title,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteQuestByTitle.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, userId);
        _argIndex = 2;
        _stmt.bindString(_argIndex, title);
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
          __preparedStmtOfDeleteQuestByTitle.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAllQuests(final String userId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAllQuests.acquire();
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
          __preparedStmtOfClearAllQuests.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<QuestEntity>> getAllQuests(final String userId) {
    final String _sql = "SELECT * FROM quests WHERE userId = ? ORDER BY generatedDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quests"}, new Callable<List<QuestEntity>>() {
      @Override
      @NonNull
      public List<QuestEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsRecovery = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecovery");
          final int _cursorIndexOfLostStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "lostStreak");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfTargetDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDuration");
          final int _cursorIndexOfIsMissed = CursorUtil.getColumnIndexOrThrow(_cursor, "isMissed");
          final int _cursorIndexOfIsIncremental = CursorUtil.getColumnIndexOrThrow(_cursor, "isIncremental");
          final int _cursorIndexOfCurrentProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "currentProgress");
          final int _cursorIndexOfTargetProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "targetProgress");
          final int _cursorIndexOfGeneratedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedDate");
          final List<QuestEntity> _result = new ArrayList<QuestEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuestEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final boolean _tmpIsRecovery;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsRecovery);
            _tmpIsRecovery = _tmp_1 != 0;
            final int _tmpLostStreak;
            _tmpLostStreak = _cursor.getInt(_cursorIndexOfLostStreak);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final int _tmpTargetDuration;
            _tmpTargetDuration = _cursor.getInt(_cursorIndexOfTargetDuration);
            final boolean _tmpIsMissed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsMissed);
            _tmpIsMissed = _tmp_2 != 0;
            final boolean _tmpIsIncremental;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsIncremental);
            _tmpIsIncremental = _tmp_3 != 0;
            final int _tmpCurrentProgress;
            _tmpCurrentProgress = _cursor.getInt(_cursorIndexOfCurrentProgress);
            final int _tmpTargetProgress;
            _tmpTargetProgress = _cursor.getInt(_cursorIndexOfTargetProgress);
            final long _tmpGeneratedDate;
            _tmpGeneratedDate = _cursor.getLong(_cursorIndexOfGeneratedDate);
            _item = new QuestEntity(_tmpId,_tmpUserId,_tmpTitle,_tmpDescription,_tmpXpReward,_tmpIsCompleted,_tmpIsRecovery,_tmpLostStreak,_tmpStartTime,_tmpEndTime,_tmpTargetDuration,_tmpIsMissed,_tmpIsIncremental,_tmpCurrentProgress,_tmpTargetProgress,_tmpGeneratedDate);
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
  public Flow<List<QuestEntity>> getActiveQuests(final String userId) {
    final String _sql = "SELECT * FROM quests WHERE userId = ? AND isCompleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quests"}, new Callable<List<QuestEntity>>() {
      @Override
      @NonNull
      public List<QuestEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsRecovery = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecovery");
          final int _cursorIndexOfLostStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "lostStreak");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfTargetDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDuration");
          final int _cursorIndexOfIsMissed = CursorUtil.getColumnIndexOrThrow(_cursor, "isMissed");
          final int _cursorIndexOfIsIncremental = CursorUtil.getColumnIndexOrThrow(_cursor, "isIncremental");
          final int _cursorIndexOfCurrentProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "currentProgress");
          final int _cursorIndexOfTargetProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "targetProgress");
          final int _cursorIndexOfGeneratedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedDate");
          final List<QuestEntity> _result = new ArrayList<QuestEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuestEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final boolean _tmpIsRecovery;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsRecovery);
            _tmpIsRecovery = _tmp_1 != 0;
            final int _tmpLostStreak;
            _tmpLostStreak = _cursor.getInt(_cursorIndexOfLostStreak);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final int _tmpTargetDuration;
            _tmpTargetDuration = _cursor.getInt(_cursorIndexOfTargetDuration);
            final boolean _tmpIsMissed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsMissed);
            _tmpIsMissed = _tmp_2 != 0;
            final boolean _tmpIsIncremental;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsIncremental);
            _tmpIsIncremental = _tmp_3 != 0;
            final int _tmpCurrentProgress;
            _tmpCurrentProgress = _cursor.getInt(_cursorIndexOfCurrentProgress);
            final int _tmpTargetProgress;
            _tmpTargetProgress = _cursor.getInt(_cursorIndexOfTargetProgress);
            final long _tmpGeneratedDate;
            _tmpGeneratedDate = _cursor.getLong(_cursorIndexOfGeneratedDate);
            _item = new QuestEntity(_tmpId,_tmpUserId,_tmpTitle,_tmpDescription,_tmpXpReward,_tmpIsCompleted,_tmpIsRecovery,_tmpLostStreak,_tmpStartTime,_tmpEndTime,_tmpTargetDuration,_tmpIsMissed,_tmpIsIncremental,_tmpCurrentProgress,_tmpTargetProgress,_tmpGeneratedDate);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
