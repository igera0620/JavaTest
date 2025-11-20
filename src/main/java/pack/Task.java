package pack;

import java.time.LocalDate;
import java.time.LocalTime;

// タスク情報を表すクラス
public class Task {
    private int id;           // タスクID
    private int userId;       // 所属ユーザーID
    private LocalDate startDate;   // 開始日
    private LocalDate endDate;     // 終了日
    private LocalTime startTime;   // 開始時間
    private LocalTime endTime;     // 終了時間
    private String title;     // タイトル
    private String content;   // 内容
    private Integer categoryId; // カテゴリーID（null許容）
    private String categoryName;   // ★ 追加
    private String categoryColor;  // ★ 追加
    private int priority;	 // 優先度

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getCategoryColor() { return categoryColor; }
    public void setCategoryColor(String categoryColor) { this.categoryColor = categoryColor; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
}
