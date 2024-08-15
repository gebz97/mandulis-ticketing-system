import { api } from "@/lib/api-client";
import { MutationConfig } from "@/lib/react-query";
import { useMutation } from "@tanstack/react-query";
import { z } from "zod";
import { CreateCommentResponse } from "../models/create-comment-response";

export const createCommentInputSchema = z.object({
  // TODO: adjust later
  content: z.string().min(5).max(150),
});

export type CreateCommentInput = z.infer<typeof createCommentInputSchema>;

export const createComment = ({
  data,
}: {
  data: CreateCommentInput;
}): Promise<CreateCommentResponse> => {
  // TODO: change later
  return api.post("/comments", data);
};

type UseCreateCommentOptions = {
  mutationConfig?: MutationConfig<typeof createComment>;
};

export const useCreateComment = ({
  mutationConfig,
}: UseCreateCommentOptions = {}) => {
  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    ...restConfig,
    mutationFn: createComment,
  });
};
