import { DashboardLayout } from "@/components/layouts/dashboard-layout";
import {
  CreateCommentInput,
  useCreateComment,
} from "@/features/comments/api/create-comment";
import { CommentCard } from "@/features/comments/components/comment-card";
import { CreateCommentForm } from "@/features/comments/components/create-comment-form";
import { mockComments } from "@/features/comments/mock";
import { TicketContent } from "@/features/tickets/components/ticket-content";
import { Card, Flex, Stack, Title, Notification } from "@mantine/core";
import { notifications } from "@mantine/notifications";
import { FC } from "react";

export const ViewTicketPage: FC = () => {
  const ticket = {
    title: "My very first ticket",
    description:
      "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
  };

  const comments = {
    count: 10,
  };

  const createComment = useCreateComment();

  const handleCreateComment = (data: CreateCommentInput) => {
    createComment.mutate(
      { data: data },
      {
        onSuccess: () => console.log("comment added"),
        onError: (e) => {
          notifications.show({
            color: "red",
            position: "top-center",
            title: "Create comment finished with error",
            withBorder: true,
            message: e.response?.data.message || e.message,
          });
        },
      }
    );
  };

  return (
    <DashboardLayout title="Ticket 123">
      <Stack>
        <Title>View: Ticket 123</Title>
        <Flex w="100%" gap="md">
          <Stack w="66%">
            <Card withBorder>
              <TicketContent
                title={ticket.title}
                description={ticket.description}
              />
            </Card>
            <Card withBorder>
              <CreateCommentForm
                onSubmit={handleCreateComment}
                isLoading={createComment.isPending}
              />
            </Card>
            <Card withBorder>
              <Title order={4}>Comments ({comments.count})</Title>
              <Stack mt="md" gap="xl">
                {mockComments.map((c) => (
                  <CommentCard key={c.id} {...c} />
                ))}
              </Stack>
            </Card>
          </Stack>
          <Card w="33%" withBorder></Card>
        </Flex>
      </Stack>
    </DashboardLayout>
  );
};
