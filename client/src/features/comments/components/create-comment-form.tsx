import { FC } from "react";
import { Controller, useForm } from "react-hook-form";
import {
  CreateCommentInput,
  createCommentInputSchema,
} from "../api/create-comment";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button, Textarea } from "@mantine/core";

interface CreateCommentFormProps {
  onSubmit: (data: CreateCommentInput) => void;
  isLoading?: boolean;
}

export const CreateCommentForm: FC<CreateCommentFormProps> = ({
  onSubmit,
  isLoading,
}) => {
  const form = useForm<CreateCommentInput>({
    resolver: zodResolver(createCommentInputSchema),
    defaultValues: {
      content: "",
    },
  });

  return (
    <form onSubmit={form.handleSubmit(onSubmit)}>
      <Controller
        control={form.control}
        name="content"
        render={({ field, fieldState: { error } }) => (
          <Textarea
            label="Your comment"
            placeholder="Start writing you comment..."
            error={error?.message}
            {...field}
          />
        )}
      />
      <Button type="submit" mt="md" size="sm" loading={isLoading}>
        Submit
      </Button>
    </form>
  );
};
