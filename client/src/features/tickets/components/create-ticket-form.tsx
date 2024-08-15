import { FC } from "react";
import { Controller, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Button,
  Group,
  MultiSelect,
  Select,
  Textarea,
  TextInput,
} from "@mantine/core";
import { CreateTicketResponse } from "../models/create-ticket-response";
import {
  CreateTicketInput,
  createTicketInputSchema,
  useCreateTicket,
} from "../api/create-ticket";

interface CreateTicketFormProps {
  onSuccess: (data: CreateTicketResponse) => void;
}

export const CreateTicketForm: FC<CreateTicketFormProps> = ({ onSuccess }) => {
  const CreateTicket = useCreateTicket();

  const form = useForm<CreateTicketInput>({
    resolver: zodResolver(createTicketInputSchema),
    defaultValues: {
      title: "",
      description: "",
      priority: "",
      assignees: [],
      category: "",
      groups: [],
      tags: [],
    },
  });

  const handleSubmit = (data: CreateTicketInput) => {
    CreateTicket.mutate(
      { data: data },
      {
        onSuccess: onSuccess,
        onError: (error) => console.log(error),
      }
    );
  };

  return (
    <form onSubmit={form.handleSubmit(handleSubmit)}>
      <Controller
        control={form.control}
        name="title"
        render={({ field, fieldState: { error } }) => (
          <TextInput
            label="Title"
            placeholder="Enter title"
            required
            error={error?.message}
            {...field}
          />
        )}
      />
      <Controller
        control={form.control}
        name="description"
        render={({ field, fieldState: { error } }) => (
          <Textarea
            label="Description"
            placeholder="Enter description"
            rows={5}
            required
            mt="md"
            error={error?.message}
            {...field}
          />
        )}
      />
      <Controller
        control={form.control}
        name="assignees"
        render={({ field, fieldState: { error } }) => (
          <MultiSelect
            label="Assignees"
            placeholder="Choose assignees"
            required
            mt="md"
            error={error?.message}
            data={[
              { value: "1", label: "test-1" },
              { value: "2", label: "test-2" },
              { value: "3", label: "test-3" },
              { value: "4", label: "test-4" },
            ]}
            {...field}
          />
        )}
      />
      <Controller
        control={form.control}
        name="groups"
        render={({ field, fieldState: { error } }) => (
          <MultiSelect
            label="Groups"
            placeholder="Choose groups"
            required
            mt="md"
            error={error?.message}
            data={[
              { value: "1", label: "group-1" },
              { value: "2", label: "group-2" },
              { value: "3", label: "group-3" },
              { value: "4", label: "group-4" },
            ]}
            {...field}
          />
        )}
      />
      <Controller
        control={form.control}
        name="priority"
        render={({ field, fieldState: { error } }) => (
          <Select
            label="Priority"
            placeholder="Choose priority"
            required
            mt="md"
            error={error?.message}
            data={[
              { value: "1", label: "priority-1" },
              { value: "2", label: "priority-2" },
              { value: "3", label: "priority-3" },
              { value: "4", label: "priority-4" },
            ]}
            {...field}
          />
        )}
      />
      <Controller
        control={form.control}
        name="category"
        render={({ field, fieldState: { error } }) => (
          <Select
            label="Category"
            placeholder="Choose category"
            required
            mt="md"
            error={error?.message}
            data={[
              { value: "1", label: "category-1" },
              { value: "2", label: "category-2" },
              { value: "3", label: "category-3" },
              { value: "4", label: "category-4" },
            ]}
            {...field}
          />
        )}
      />
      <Controller
        control={form.control}
        name="tags"
        render={({ field, fieldState: { error } }) => (
          <MultiSelect
            label="Tags"
            placeholder="Choose tags"
            required
            mt="md"
            error={error?.message}
            data={[
              { value: "1", label: "tag-1" },
              { value: "2", label: "tag-2" },
              { value: "3", label: "tag-3" },
              { value: "4", label: "tag-4" },
            ]}
            {...field}
          />
        )}
      />
      <Group gap="sm" mt="md" justify="end">
        <Button variant="light" color="dark" component="a" href="/">
          Cancel
        </Button>
        <Button type="submit">Submit</Button>
      </Group>
    </form>
  );
};
