"use client"

import * as React from "react"
import { CalendarIcon } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Calendar } from "@/components/ui/calendar"
import { Input } from "@/components/ui/input"
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover"

function formatDate(date: Date | undefined) {
  if (!date) {
    return ""
  }

  return date.toLocaleDateString("pt-BR", {
    day: "2-digit",
    month: "long",
    year: "numeric",
  })
}

interface DatePickerProps {
  value?: Date
  onChange?: (date: Date | undefined) => void
}

function isValidDate(d: Date) {
  return d instanceof Date && !isNaN(d.getTime());
}

export function DatePicker({ value, onChange }: DatePickerProps) {
  const [open, setOpen] = React.useState(false)
  const [month, setMonth] = React.useState<Date | undefined>(value)
  const [inputValue, setInputValue] = React.useState(
    value ? value.toLocaleDateString("pt-BR") : ""
  );

  React.useEffect(() => {
    setInputValue(value ? value.toLocaleDateString("pt-BR") : "");
  }, [value]);

  React.useEffect(() => { console.log(value) }, [value])

  return (
    <div className="flex flex-col gap-3 w-full">
      <div className="relative flex gap-2">
        <Input
          id="date"
          value={inputValue}
          placeholder="dd/mm/aaaa"
          className="bg-background pr-10"
          onChange={(e) => {
            setInputValue(e.target.value);
            const date = new Date(e.target.value);
            if (isValidDate(date)) {
              onChange?.(date);
            } else {
              onChange?.(undefined);
            }
          }}
          onKeyDown={(e) => {
            if (e.key === "ArrowDown") {
              e.preventDefault()
              setOpen(true)
            }
          }}
        />
        <Popover open={open} onOpenChange={setOpen}>
          <PopoverTrigger asChild>
            <Button
              id="date-picker"
              variant="ghost"
              className="absolute top-1/2 right-2 size-6 -translate-y-1/2"
            >
              <CalendarIcon className="size-3.5" />
              <span className="sr-only">Select date</span>
            </Button>
          </PopoverTrigger>
          <PopoverContent
            className="w-auto overflow-hidden p-0"
            align="end"
            alignOffset={-8}
            sideOffset={10}
          >
            <Calendar
              mode="single"
              selected={value}
              captionLayout="dropdown"
              month={month}
              onMonthChange={setMonth}
              onSelect={(date) => {
                if (!date) return
                onChange?.(date)
                setInputValue(formatDate(date))
                setOpen(false)
              }}
            />
          </PopoverContent>
        </Popover>
      </div>
    </div>
  )
}
