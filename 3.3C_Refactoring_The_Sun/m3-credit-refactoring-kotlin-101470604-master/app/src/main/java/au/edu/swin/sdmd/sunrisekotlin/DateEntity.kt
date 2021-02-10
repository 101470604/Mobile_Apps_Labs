package au.edu.swin.sdmd.sunrisekotlin

class DateEntity {

    var year : Int
    var month : Int
    var day : Int

    constructor()
    {
        // We start at the meme computer science date
        this.year = 1970
        this.month = 1
        this.day = 1
    }

    private fun isLeapYear(year : Int) : Boolean
    {
        return ((((year % 4) == 0)
                && (year % 100) != 0)
                || (year % 400) == 0)
    }

    private fun dateIsValid(year : Int, month : Int,day : Int) : Boolean
    {
        if (month > 12 || month < 1) return false
        if (day > 31 || day < 1) return false

        if (month == 2)
            if  (isLeapYear(year))
                if (day != 29)
                    return false

        if (isMonthWithThirtyDays(month))
            if (day == 31)
                return false

        return true
    }

    fun setDate(year : Int, month : Int,day : Int)
    {
        if (dateIsValid(year, month, day))
        {
            this.year = year
            this.month = month
            this.day = day
        }
    }

    override fun equals(other: Any?): Boolean {
        if(other == null || other !is DateEntity)
            return false
        else
            return (this.year  == other.year &&
                    this.month == other.month &&
                    this.day   == other.day)
    }

    fun isInFuture(date : DateEntity) : Boolean
    {
        return (this.year >= date.year) &&
                (this.month >= date.month) &&
                (this.day > date.day)
    }

    fun isInPast(date : DateEntity) : Boolean
    {
        return (this.year <= date.year) &&
                (this.month <= date.month) &&
                (this.day < date.day)
    }

    private fun isMonthWithThirtyDays(month : Int) : Boolean
    {
        return (month == 4 || month == 6 || month == 9 || month == 11)
    }

    private fun isMonthWithThirtyOneDays(month : Int) : Boolean
    {
        return (month == 1 || month == 3 || month == 5 ||
                month == 7 || month == 8 || month == 9 ||
                month == 10 || month == 12)
    }

    fun incrementMonth()
    {
        if (month == 12)
        {
            this.year++
            this.month = 1
        }
        else
        {
            this.month++
        }
        this.day = 1
    }

    fun incrementDay()
    {
        if (day < 28)
            this.day++
        else if (isMonthWithThirtyOneDays(this.month))
        {
            if (this.day == 31)
            {
                incrementMonth()
                this.day = 1
            }
            else
                this.day++
        }
        else if (this.month != 2)
        {
            if (this.day == 30)
            {
                incrementMonth()
                this.day = 1
            }
            else
                this.day++
        }
        else
        {
            if (this.day == 28)
            {
                if (isLeapYear(this.year))
                    this.day++
                else
                {
                    incrementMonth()
                    this.day = 1
                }
            }
        }
    }
}
